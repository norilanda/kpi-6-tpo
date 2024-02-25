package Algorithms;

import Matrix.IMatrix;
import Matrix.Matrix;
import Matrix.MatrixHelper;

public class ParallelFoxMatrixMultiplication implements IMatrixMultiplication{
    private final int rowFragmentsNumber;
    public ParallelFoxMatrixMultiplication(int rowFragmentsNumber) {
        this.rowFragmentsNumber = rowFragmentsNumber;
    }
    @Override
    public IMatrix Multiply(IMatrix A, IMatrix B) {
        int[][] matrixC = new int[A.getRowNumber()][A.getColumnNumber()];

        var matrixA = A.getMatrix();
        var matrixB = B.getMatrix();

        var size = A.getRowNumber();

        var blocksA = MatrixHelper.spitMatrixToFragments(matrixA, rowFragmentsNumber);
        var blocksB = MatrixHelper.spitMatrixToFragments(matrixB, rowFragmentsNumber);

        int fragmentSize = size / rowFragmentsNumber;

        var syncBlocksA = getSyncBlocksA(blocksA, rowFragmentsNumber);
        var syncBlocksB = getSyncBlocksB(blocksB, rowFragmentsNumber);

        var threads = new Thread[rowFragmentsNumber * rowFragmentsNumber];

        for (int i = 0; i < threads.length; i++) {
            var iIndex = i / rowFragmentsNumber;
            var jIndex = i % rowFragmentsNumber;

            var blockAIndex = iIndex * rowFragmentsNumber + iIndex;
            var blockBIndex = i;

            var foxThread = new FoxMatrixThread(
                    iIndex, jIndex,
                    syncBlocksA[blockAIndex], syncBlocksA[blockAIndex].nextBlock,
                    syncBlocksB[blockBIndex], syncBlocksB[blockBIndex].nextBlock,
                    matrixC, rowFragmentsNumber, fragmentSize);
            threads[i] = new Thread(foxThread);
            threads[i].start();
        }

        for (Thread thread : threads ) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return new Matrix(matrixC);
    }

    private SyncBlock[] getSyncBlocksA(int[][][] blocks, int blocksPerRow) {
        var syncBlocks = initSyncBlocks(blocks);

        int offset = 0;
        while (offset < syncBlocks.length) {
            for (int i = 0; i < blocksPerRow; i++) {
                var nextIndex = (i + 1 + blocksPerRow) % blocksPerRow + offset;
                syncBlocks[offset + i].nextBlock = syncBlocks[nextIndex];
            }
            offset += blocksPerRow;
        }

        return syncBlocks;
    }

    private SyncBlock[] getSyncBlocksB(int[][][] blocks, int blocksPerRow) {
        var syncBlocks = initSyncBlocks(blocks);

        for (int i = 0; i < syncBlocks.length; i++) {
            var nextIndex = (i + blocksPerRow + syncBlocks.length) % syncBlocks.length;
            syncBlocks[i].nextBlock = syncBlocks[nextIndex];
        }

        return syncBlocks;
    }

    private SyncBlock[] initSyncBlocks(int[][][] blocks) {
        var syncBlocks = new SyncBlock[blocks.length];
        for (int i = 0; i < syncBlocks.length; i++) {
            syncBlocks[i] = new SyncBlock();
            syncBlocks[i].block = blocks[i];
        }

        return syncBlocks;
    }
}

class SyncBlock {
    public boolean isReady = false;
    public int[][] block;
    public SyncBlock nextBlock;
}

class FoxMatrixThread implements Runnable {
    private int iIndex;
    private int jIndex;
    private SyncBlock blockA;
    private SyncBlock nextBlockA;
    private SyncBlock blockB;
    private SyncBlock nextBlockB;
    private int[][] matrixC;
    private int rowFragmentsNumber;
    private int fragmentSize;

    public FoxMatrixThread(int iIndex, int jIndex,
                           SyncBlock blockA, SyncBlock nextBlockA,
                           SyncBlock blockB, SyncBlock nextBlockB,
                           int[][] matrixC, int rowFragmentsNumber, int fragmentSize) {
        this.iIndex = iIndex;
        this.jIndex = jIndex;
        this.blockA = blockA;
        this.nextBlockA = nextBlockA;
        this.blockB = blockB;
        this.nextBlockB = nextBlockB;
        this.matrixC = matrixC;
        this.rowFragmentsNumber = rowFragmentsNumber;
        this.fragmentSize = fragmentSize;
    }

    @Override
    public void run() {
        for (int iteration = 0; iteration < rowFragmentsNumber; iteration++) {
            synchronized (blockB) {
                blockB.isReady = false;
            }
            var blockC = MatrixHelper.multiplyBlocks(blockA.block, blockB.block);
            MatrixHelper.addBlockToMatrix(blockC, matrixC, iIndex * fragmentSize, jIndex * fragmentSize);

            notifyBlockIsFree(blockB);
//            System.out.println(Thread.currentThread().getName() +  " released blockB ");

            blockA = nextBlockA;
            nextBlockA = nextBlockA.nextBlock;

            waitForNextBlock(nextBlockB);
            blockB = nextBlockB;
            nextBlockB = nextBlockB.nextBlock;
//            System.out.println(Thread.currentThread().getName() +  " get blockB ");
        }
    }

    private void notifyBlockIsFree(SyncBlock block) {
        synchronized (block) {
            block.isReady = true;
            block.notifyAll();
        }
    }

    private void waitForNextBlock(SyncBlock block) {
        synchronized(block) {
            while (!block.isReady) {
                try {
//                    System.out.println("waiting in thread " + Thread.currentThread().getName());
                    block.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}