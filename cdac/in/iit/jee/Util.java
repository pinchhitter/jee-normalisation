package cdac.in.iit.jee;

import java.util.Map;
import java.util.List;
import java.util.TreeMap;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Collections;

public class Util{

	public static int compare(Candidate candidate1, Candidate candidate2) {

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

	public static int compareNor(Candidate candidate1, Candidate candidate2) {

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
