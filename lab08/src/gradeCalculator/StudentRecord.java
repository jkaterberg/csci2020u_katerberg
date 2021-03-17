package gradeCalculator;

public class StudentRecord {
    private String studentID;
    private float midterm;
    private float assignments;
    private float finalExam;
    private float finalMark;
    private char letter;

    public StudentRecord(String studentID, float midterm, float assignments, float finalExam){
        this.studentID = studentID;
        this.midterm = midterm;
        this.assignments = assignments;
        this.finalExam = finalExam;
        this.finalMark = calcFinal();
        this.letter = calcLetter();
    }

    //Getters
    public String getStudentID(){ return this.studentID; }
    public float getMidterm(){ return this.midterm; }
    public float getAssignments(){ return this.assignments; }
    public float getFinalExam(){ return this.finalExam; }
    public float getFinalMark(){ return this.finalMark; }
    public char getLetter(){ return this.letter; }

    //Setters
    public void setStudentID(String id){ this.studentID=id; }
    public void setMidterm(float grade){ this.midterm=grade; }
    public void setAssignments(float grade){ this.assignments=grade; }
    public void setFinalExam(float grade){ this.finalExam=grade; }

    /*
    Function to calculate the final grade for the student

    @returns Student's final grade
     */
    private float calcFinal(){
        return (float) (0.2*this.assignments + 0.3*this.midterm + 0.5*this.finalExam);
    }

    /*
    Function to determine student's letter grade

    @return letter grade
     */
    private char calcLetter(){
        float f = this.finalMark;
        if(f < 50){
            return 'F';
        }else if(f<60 && f>=50){
            return 'D';
        }else if(f<70 && f>=60){
            return 'C';
        }else if(f<80 && f>=70){
            return 'B';
        }else{
            return 'A';
        }
    }

}
