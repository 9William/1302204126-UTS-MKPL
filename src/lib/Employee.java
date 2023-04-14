package lib;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

class EmployeeDetails {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String address;

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getLastName() {
        return lastName;
    }
}

public class Employee extends EmployeeDetails{

    private LocalDate dateJoin;
    private int monthWorkingInYear;

    private boolean isForeigner;
    private boolean gender; //true = Laki-laki, false = Perempuan

    private int monthlySalary;
    private int otherMonthlyIncome;
    private int annualDeductible;

    private String spouseName;
    private String spouseIdNumber;

    private List<String> childNames;
    private List<String> childIdNumbers;

    public Employee(EmployeeDetails eDetails, LocalDate dateJoin, boolean isForeigner, boolean gender) {
        this.setEmployeeId(eDetails.getEmployeeId());
        this.setFirstName(eDetails.getFirstName());
        this.setLastName(eDetails.getLastName());
        this.setIdNumber(eDetails.getIdNumber());
        this.setAddress(eDetails.getAddress());
        this.dateJoin = dateJoin;
        this.isForeigner = isForeigner;
        this.gender = gender;

        childNames = new LinkedList<String>();
        childIdNumbers = new LinkedList<String>();
    }

    /**
     * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade
     * kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per
     * bulan, grade 3: 7.000.000 per bulan) Jika pegawai adalah warga negara
     * asing gaji bulanan diperbesar sebanyak 50%
     */
    public void setMonthlySalary(int grade) {
        if (grade == 1) {
            monthlySalary = 3000000;
        } else if (grade == 2) {
            monthlySalary = 5000000;
        } else if (grade == 3) {
            monthlySalary = 7000000;
        }
        if (isForeigner) {
                monthlySalary = (int) (3000000 * 1.5);
            }
    }

    public void setAnnualDeductible(int deductible) {
        this.annualDeductible = deductible;
    }

    public void setAdditionalIncome(int income) {
        this.otherMonthlyIncome = income;
    }

    public void setSpouse(String spouseName, String spouseIdNumber) {
        this.spouseName = spouseName;
        this.spouseIdNumber = idNumber;
    }

    public void addChild(String childName, String childIdNumber) {
        childNames.add(childName);
        childIdNumbers.add(childIdNumber);
    }

    public int getAnnualIncomeTax() {

        //Menghitung berapa lama pegawai bekerja dalam setahun ini, jika pegawai sudah bekerja dari tahun sebelumnya maka otomatis dianggap 12 bulan.
        LocalDate date = LocalDate.now();

        if (date.getYear() == dateJoin.getYear()) {
            monthWorkingInYear = date.getMonthValue() - dateJoin.getMonthValue();
        } else {
            monthWorkingInYear = 12;
        }

        return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthWorkingInYear, annualDeductible, spouseIdNumber.equals(""), childIdNumbers.size());
    }
}
