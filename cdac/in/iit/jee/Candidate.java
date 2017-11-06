package cdac.in.iit.jee;

import java.util.Map;
import java.util.TreeMap;
import java.util.Comparator;

class Subject{

	String subject;
	String sessionId;
	double marks;
	double normaliseMarks;

	Subject(String subject){

		this.subject = subject;
		this.marks = 0.0d;
		this.normaliseMarks = 0.0d;
		this.sessionId = null;
	}

	Subject(String subject, double marks, String sessionId){

		this.subject = subject;
		this.marks = marks;
		this.normaliseMarks = 0.0d;
		this.sessionId = sessionId;
	}
}

public class Candidate{

	String id;
	String sessionId;
	double totalMarks;
	double totalNormalisedMarks;
	int rank;
	int normalisedRank;
	Map<String, Subject> subjects;


	public double getMarks(){
		return totalMarks;
	}


	public double getPhyMarks(){
		return subjects.get("PHY").marks;
	}

	public double getCheMarks(){
		return subjects.get("CHE").marks;
	}

	public double getMatMarks(){
		return subjects.get("MAT").marks;
	}

	public double getNRMarks(){
		return totalNormalisedMarks;
	}

	public double getPhyNorMarks(){
		return subjects.get("PHY").normaliseMarks;
	}

	public double getCheNorMarks(){
		return subjects.get("CHE").normaliseMarks;
	}

	public double getMatNorMarks(){
		return subjects.get("MAT").normaliseMarks;
	}

	Candidate(String id){

		this.id = id;
		this.totalMarks = 0.0d;
		this.totalNormalisedMarks = 0.0d;
		this.rank = -1;
		this.normalisedRank = -1;
		this.subjects = new TreeMap<String, Subject>();
		this.sessionId = null;
	} 

	Candidate(String id, double mat, double phy, double che, String sessionId){

		this.id = id;
		this.totalMarks = mat + phy + che;
		this.totalNormalisedMarks = 0.0d;
		this.rank = -1;
		this.normalisedRank = -1;
		this.sessionId = sessionId;
		this.subjects = new TreeMap<String, Subject>();
		this.subjects.put("MAT", new Subject("MAT", mat, sessionId) );
		this.subjects.put("PHY", new Subject("PHY", phy, sessionId) );
		this.subjects.put("CHE", new Subject("CHE", che, sessionId) );
	}

	public static void header(){

		System.out.println("ID, Total-Normalised, AIR-Normalised-Rank, Total-Marks, AIR-Rank, CHE(Session), CHE-Marks, CHE-Nor-Marks, MAT(Session), MAT-Marks, MAT-Nor-Marks, PHY(Session), PHY-Marks, PHY-Nor-Marks");
	}

	void print(){

		System.out.print(id+", "+totalNormalisedMarks+", "+normalisedRank+", "+totalMarks+", "+rank);
		for(String sub: this.subjects.keySet() ){
			Subject subject = this.subjects.get( sub );
			System.out.print(", "+sub+"("+subject.sessionId+"), "+subject.marks+", "+subject.normaliseMarks);
		}
		System.out.println();
	}
}


class NorMarksComp implements Comparator<Candidate>{
	@Override
	public int compare(Candidate candidate1, Candidate candidate2) {


		if( candidate1.getNRMarks() < candidate2.getNRMarks() )
			return 1;
		if( candidate1.getNRMarks() > candidate2.getNRMarks() )
			return -1;

		long d1 = Double.doubleToLongBits(candidate1.getNRMarks());
		long d2 = Double.doubleToLongBits(candidate2.getNRMarks());

		if( d1 == d2 ){

			if( candidate1.getMatNorMarks() < candidate2.getMatNorMarks() )
				return 1;
			else if ( candidate1.getMatNorMarks() > candidate2.getMatNorMarks() )
				return -1;

			d1 = Double.doubleToLongBits(candidate1.getMatNorMarks() );
			d2 = Double.doubleToLongBits(candidate2.getMatNorMarks() );

			if( d1 == d2  ){

				if( candidate1.getPhyNorMarks() < candidate2.getPhyNorMarks() )
					return 1;
				else if ( candidate1.getPhyNorMarks() > candidate2.getPhyNorMarks() )
					return -1;

				d1 = Double.doubleToLongBits(candidate1.getPhyNorMarks() );
				d2 = Double.doubleToLongBits(candidate2.getPhyNorMarks() );

				if( d1 == d2 ){

					if( candidate1.getCheNorMarks() < candidate2.getCheNorMarks() )
						return 1;
					else if ( candidate1.getCheNorMarks() > candidate2.getCheNorMarks() )
						return -1;

					d1 = Double.doubleToLongBits(candidate1.getCheNorMarks() );
					d2 = Double.doubleToLongBits(candidate2.getCheNorMarks() );
					return (d1 == d2 ?  0 : // Values are equal
							(d1 < d2 ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
							 1));                          // (0.0, -0.0) or (NaN, !NaN)
				}else if ( d1 < d2){
					return -1;
				}else{
					return 1;
				}

			}else if ( d1 < d2){
				return -1;
			}else{
				return 1;
			}

		}else if ( d1 < d2 ){
			return -1;
		}else{
			return 1;
		}

	}
}

class MarksComp implements Comparator<Candidate>{
	@Override
	public int compare(Candidate candidate1, Candidate candidate2) {

		if( candidate1.getMarks() < candidate2.getMarks() )
			return 1;
		if( candidate1.getMarks() > candidate2.getMarks() )
			return -1;

		long d1 = Double.doubleToLongBits(candidate1.getMarks());
		long d2 = Double.doubleToLongBits(candidate2.getMarks());

		if( d1 == d2 ){

			if( candidate1.getMatMarks() < candidate2.getMatMarks() )
				return 1;
			else if ( candidate1.getMatMarks() > candidate2.getMatMarks() )
				return -1;

			d1 = Double.doubleToLongBits(candidate1.getMatMarks() );
			d2 = Double.doubleToLongBits(candidate2.getMatMarks() );

			if( d1 == d2  ){

				if( candidate1.getPhyMarks() < candidate2.getPhyMarks() )
					return 1;
				else if ( candidate1.getPhyMarks() > candidate2.getPhyMarks() )
					return -1;

				d1 = Double.doubleToLongBits(candidate1.getPhyMarks() );
				d2 = Double.doubleToLongBits(candidate2.getPhyMarks() );

				if( d1 == d2 ){

					if( candidate1.getCheMarks() < candidate2.getCheMarks() )
						return 1;
					else if ( candidate1.getCheMarks() > candidate2.getCheMarks() )
						return -1;

					d1 = Double.doubleToLongBits(candidate1.getCheMarks() );
					d2 = Double.doubleToLongBits(candidate2.getCheMarks() );
					return (d1 == d2 ?  0 : // Values are equal
							(d1 < d2 ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
							 1));                          // (0.0, -0.0) or (NaN, !NaN)
				}else if ( d1 < d2){
					return -1;
				}else{  
					return 1;
				}       

			}else if ( d1 < d2){
				return -1;
			}else{  
				return 1;
			}       

		}else if ( d1 < d2 ){
			return -1; 
		}else{  
			return 1;
		}       
	}
}

