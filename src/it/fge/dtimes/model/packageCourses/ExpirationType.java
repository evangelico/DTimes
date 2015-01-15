package it.fge.dtimes.model.packageCourses;

public enum ExpirationType {
    ANNUALE(12), MENSILE(1), BIMESTRALE(2), TRIMESTRALE(3), SEMESTRALE(6), GIORNALIERA(1);

    private int monthNumber;

    public int getMonthNumber() {
	return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
	this.monthNumber = monthNumber;
    }

    private ExpirationType(int monthNumber) {
	this.monthNumber = monthNumber;
    }

}
