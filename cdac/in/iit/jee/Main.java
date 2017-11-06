package cdac.in.iit.jee;

import java.util.Map;
import java.util.List;
import java.util.TreeMap;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Collections;

public class Main{

	Map<String, Paper> papers;
	List<Candidate> candidates;

	Main(){
		this.papers = new TreeMap<String, Paper>();
		this.candidates = new ArrayList<Candidate>();
	}	

	void addPaper(String subject, Candidate candidate){

		Paper paper = papers.get(subject);

		if( paper == null){
			paper = new Paper(subject);
		}

		Session session = paper.sessions.get( candidate.sessionId );

		if(session == null){
			session = new Session( candidate.sessionId );
		}

		session.candidates.add( candidate );
		session.marks.add( candidate.subjects.get( subject ).marks );

		paper.marks.add( candidate.subjects.get( subject ).marks  );
		paper.sessions.put(candidate.sessionId, session);

		paper.candidates.add( candidate );
		papers.put(subject, paper);
	}

	void read(String filename, boolean header){

		String line = null;
		BufferedReader br = null;
		int count = 0;
		try{	
			br = new BufferedReader(new FileReader( new File(filename)) );
			while( (line = br.readLine() ) != null ){

				count++;
				if( header ){
					header = false;
					continue;
				}

				String token[] = line.split(",");
				String id = token[0].trim();
				String sessionId = token[1].trim();

				double che = Double.parseDouble( token[2].trim() );
				double mat = Double.parseDouble( token[3].trim() );
				double phy = Double.parseDouble( token[4].trim() );

				Candidate candidate = new Candidate( id, mat, phy, che, sessionId);	
				addPaper("PHY", candidate );
				addPaper("CHE", candidate );
				addPaper("MAT", candidate );
				candidates.add( candidate );
			}

		}catch(Exception e){
			System.out.println("Line("+count+"): "+line);
			e.printStackTrace();
		}finally{
			if( br != null){
				try{
					br.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	void normalised(){

		for( String paper: papers.keySet() ){
			papers.get( paper ).normalised();
		}

		for(Candidate candidate: candidates){
			candidate.totalNormalisedMarks = candidate.subjects.get("PHY").normaliseMarks + candidate.subjects.get("CHE").normaliseMarks + candidate.subjects.get("MAT").normaliseMarks;
		}
	}

	void ranking(){

		Collections.sort( candidates, new MarksComp() );
		double oldMarks = 99999.0d;	
		int rank = 0;
		Candidate prevCandidate = null;
		for(Candidate candidate: candidates){
			double marks = candidate.getMarks();
			if( oldMarks > marks ){
				rank++;
			}else if ( oldMarks == marks ){
				if( Util.compare( prevCandidate, candidate ) != 0 ){
					rank++;	
				}else{
					System.err.println("Tie beetween: "+candidate.id+", "+prevCandidate.id);
				}
			} 
			candidate.rank = rank;
			oldMarks = marks;
			prevCandidate = candidate;
		}	

		Collections.sort( candidates, new NorMarksComp() );
		oldMarks = 99999.0d;	
		rank = 0;
		prevCandidate = null;
		for(Candidate candidate: candidates){
			double marks = candidate.getNRMarks();
			if( oldMarks > marks ){
				rank++;
			}else if ( oldMarks == marks ){
				if( Util.compareNor( prevCandidate, candidate ) != 0 ){
					rank++;	
				}else{
					System.err.println("Tie beetween: "+candidate.id+", "+prevCandidate.id);
				}
			} 
			candidate.normalisedRank = rank;
			oldMarks = marks;
			prevCandidate = candidate;
		}	
	}

	void print(){
		for(Candidate candidate: candidates){
			candidate.print();
		}		
	}

	public static void main(String[] args){

		Main jeemain = new Main();
		jeemain.read(args[0], true);
		jeemain.normalised();
		jeemain.ranking();
		jeemain.print();	 		
	}
}
