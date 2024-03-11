package GradeBook;

public class GradeBook {
    private final GradeBookPage[] pages;

    public GradeBook(GradeBookPage[] pages) {
        this.pages = pages;
    }

    public GradeBookPage getPageForGroup(int groupIndex) {
        if (groupIndex >= pages.length) {
            throw new IllegalArgumentException("Page is out of range");
        }
        return pages[groupIndex];
    }

    public int length() {
        return pages.length;
    }

    public int getWeekNumber() {
        return pages[0].getWeekNumber();
    }
}
