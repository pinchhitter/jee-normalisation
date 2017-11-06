package cdac.in.iit.jee;

import java.util.Map;
import java.util.List;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util. Collections;

class Session{

	String id;
	ArrayList<Candidate> candidates;
	ArrayList<Double> marks;

	int zeroPointOnePercent;
	double mTBar;
        double mQ;
        double mean;
        double stdDev;
        double maxRawScore;
        double minRawScore;

	Session(String id){

		this.id = id;
		this.candidates  = new ArrayList<Candidate>();
		this.marks = new ArrayList<Double>();
		this.mTBar = 0.0d;
		this.mQ = 0.0d;
		this.mean = 0.0d;
		this.stdDev = 0.0d;
		this.maxRawScore = 0.0d;
		this.minRawScore = 0.0d;
		this.zeroPointOnePercent = 0;
	}

	void calStats(){

		Collections.sort( marks, Collections.reverseOrder() );
                double [] lmarks = StdStats.toArray( marks );

                this.zeroPointOnePercent = ( int ) Math.ceil( ( (double) lmarks.length) / 1000 );
                this.mean = StdStats.mean( lmarks );

                /*
                   System.err.println("Mean: "+marks.length );
                   System.err.println("Mean (Sum) :"+ StdStats.sum(marks) );
                   System.err.println("Mean (mean) :"+ StdStats.mean(marks) );
                   System.err.println("-------------------------------------");
                   for(int i = 0; i < marks.length; i++){
                   System.err.println((i+1)+":"+ marks[i] );
                   }
                   System.err.println("-------------------------------------");

                */

                this.stdDev = StdStats.stddev( lmarks );
                this.mTBar = StdStats.mean( lmarks, 0, zeroPointOnePercent - 1 );
                this.mQ = this.mean + this.stdDev;
                this.maxRawScore = StdStats.max( lmarks );
                this.minRawScore = StdStats.min( lmarks );

	}

        void print(){

                System.out.format(" ________________________________________%n");
                System.out.format("| Session ID             | %-13s |%n", id);
                System.out.format("|________________________|_______________|%n");
                System.out.format("| Total Candidates       | %-13d |%n", marks.size() );
                System.out.format("| 0.1 %% Candidate(s)     | %-13d |%n", zeroPointOnePercent );
                System.out.format("| Avg Of 0.1%%(s)         | %-13f |%n",mTBar );
                System.out.format("| Mean(s) + stdDev(s)    | %-13f |%n",mQ );
                System.out.format("| Mean(s)                | %-13f |%n",mean );
                System.out.format("| stdDev(s)              | %-13f |%n",stdDev );
                System.out.format("| max                    | %-13f |%n",maxRawScore );
                System.out.format("| min                    | %-13f |%n",minRawScore );
                System.out.format("|________________________|_______________|%n%n");

        }


}

public class Paper{

	String paper;
	ArrayList<Candidate> candidates;
	Map<String, Session> sessions;

	ArrayList<Double> marks;
	ArrayList<Double> normalisedMarks;

        int maxOf10OR01per;
	int zeroPointOnePercent;

	boolean multisession;

	double mTgBar;
        double mQg;
        double mQ;
        double mTBar;
        double mean;
        double stdDev;

	double maxMarks;
	double minMarks;

	Paper(String paper){

		this.paper = paper;
		this.candidates = new ArrayList<Candidate>();
		this.sessions = new TreeMap<String, Session>();
		this.marks = new ArrayList<Double>();
		this.normalisedMarks = new ArrayList<Double>();

		this.zeroPointOnePercent = 0;
		this.maxOf10OR01per = 0;

		this.mTgBar = 0.0d;
		this.mQg = 0.0d;
		this.mQ = 0.0d;
		this.mTBar = 0.0d;
		this.mean = 0.0d;
		this.stdDev = 0.0d;
		this.maxMarks = 0.0d;
		this.minMarks = 0.0d;
	}

	 void calStats(){

                Collections.sort( marks, Collections.reverseOrder());
                double [] lmarks = StdStats.toArray( marks );
                this.maxMarks = StdStats.max( lmarks ) ;
                this.minMarks = StdStats.min( lmarks ) ;

                this.zeroPointOnePercent = ( int ) Math.ceil( ( (double) lmarks.length) / 1000 );
                this.maxOf10OR01per = (int) Math.max( zeroPointOnePercent, 10 );

                this.mTgBar = StdStats.mean( lmarks, 0, zeroPointOnePercent - 1 );

                /*
                   for(int i = 0;  i < 186; i++)
                   System.out.print(marks[i]+" + " );    
                   System.out.println(" => "+ marks.length);        
                   */

                this.mTBar = StdStats.mean( lmarks, 0, maxOf10OR01per - 1 );
                this.mean = StdStats.mean( lmarks );
                this.stdDev = StdStats.stddev( lmarks );
                this.mQg = mean + stdDev;
                this.mQ = Math.max( mQg , 25 );

                if( sessions.size() > 1)
              		this.multisession = true;

		for(String sessionId: sessions.keySet() ){
			this.sessions.get(sessionId).calStats();
		}
        }

	void normalised(){

		calStats();

		for(String sessionId: sessions.keySet() ){

			Session session = sessions.get( sessionId );

			for(Candidate candidate: session.candidates ){

				 candidate.subjects.get( paper ).normaliseMarks = ( (mTgBar - mQg) / (session.mTBar - session.mQ ) ) * ( candidate.subjects.get( paper ).marks - session.mQ ) + mQg;

				 //candidate.normalisedMark = ( (mTgBar - mQg) / (sn.mTBar - sn.mQ ) ) * ( actualMark - sn.mQ ) + mQg;
                                 //System.out.println(" (  ("+mTgBar+" - "+mQg+") / ( "+sn.mTBar +" - "+ sn.mQ +" ) ) * ( "+ actualMark+" - "+sn.mQ+" ) +"+ mQg +" = "+candidate.normalisedMark);
			}
		}
	}
}
