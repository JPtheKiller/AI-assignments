
import java.util.*;

//Data structure that represents plays. Has position i, j of play in board and who played.
class pos{

	int i;
	int j;
	int player;

	pos(int a, int b,int p){

		i = a;
		j = b;
		player = p;
	}

}

class connect{

	   static int difficulty=8;
	   static int ai=0;
       static int game[][] = new int[6][7];
       
       //Returns value of a segment. Receives how many X's and O's in segment, which player is the AI and who is playing. 
       public static int evaluateAux(int noPlayer,int noBot,int bot){
    	   
    	   if(bot == 2)
			   if(noPlayer == 4)
				return -512;
			   else if(noBot == 4)
				return 512;
			   else if((noPlayer == 3) && (noBot == 0))
			    return -50;
			   else if((noPlayer == 2) && (noBot == 0))
			    return -10;
			   else if((noPlayer == 1) && (noBot == 0))
			    return -1;
			   else if((noPlayer == 0) && (noBot == 1))
			    return 1;
			   else if((noPlayer == 0) && (noBot == 2))
			    return 10;
			   else if((noPlayer == 0) && (noBot == 3))
			    return 50;
			   else return 0;
    	   else {
    		   if(noPlayer == 4)
   				return 512;
   			   else if(noBot == 4)
   				return -512;
   			   else if((noPlayer == 3) && (noBot == 0))
   			    return 50;
   			   else if((noPlayer == 2) && (noBot == 0))
   			    return 10;
   			   else if((noPlayer == 1) && (noBot == 0))
   			    return 1;
   			   else if((noPlayer == 0) && (noBot == 1))
   			    return -1;
   			   else if((noPlayer == 0) && (noBot == 2))
   			    return -10;
   			   else if((noPlayer == 0) && (noBot == 3))
   			    return -50;
   			   else return 0; 
    	   }
	   }
       
       //Returns Evaluation of a board. Receives a board, which player is the AI and who is playing.
	   public static int evaluate(int M[][],int bot,int playing){
		int out=0;
		int evaluation;
		int no0 = 0;

		for(int i = 0; i<6; i++)
		  for(int j = 0; j<7; j++){

			  if(M[i][j] == 0)
				no0++;

			  int noBot = 0;
			  int noPlayer = 0;
			  //check horizontal
			  if((j+3)<7){
				  for(int k = j;k<=(j+3);k++)
					  if(M[i][k] == 1)
						noPlayer++;
					  else if(M[i][k] == 2)
						noBot++;

				  evaluation = evaluateAux(noPlayer,noBot,bot);
				  if((evaluation == 512) || (evaluation == -512) ){
					  out = evaluation;
					  i = 6;
					  break;
				  }else out+= evaluation;
			  }


			  noBot = 0;
			  noPlayer = 0;
			  //check vertical
			  if((i+3)<6){
				   for(int k = i;k<=(i+3);k++)
					  if(M[k][j] == 1)
						noPlayer++;
					  else if(M[k][j] == 2)
						noBot++;

				  evaluation = evaluateAux(noPlayer,noBot,bot);
				  if((evaluation == 512) || (evaluation == -512) ){
					  out = evaluation;
					  i = 6;
					  break;
				  }else out+= evaluation;
			  }

			  noBot = 0;
			  noPlayer = 0;
			  //check right diagonal
			  if( ((j+3)<7) && ((i+3)<6) ){
					for(int k = 0;k<=3;k++)
					  if(M[i+k][j+k] == 1)
						noPlayer++;
					  else if(M[i+k][j+k] == 2)
						noBot++;
				  evaluation = evaluateAux(noPlayer,noBot,bot);
				  if((evaluation == 512) || (evaluation == -512) ){
					  out = evaluation;
					  i = 6;
					  break;
				  }else out+= evaluation;
			  }
			  
			  noBot = 0;
			  noPlayer = 0;
			  //check left diagonal
			  if( ((j-3)>=0) && ((i+3)<6) ){
				  
				  
					for(int k = 0;k<=3;k++)
					  if(M[i+k][j-k] == 1)
						noPlayer++;
					  else if(M[i+k][j-k] == 2)
						noBot++;

				  evaluation = evaluateAux(noPlayer,noBot,bot);
				  if((evaluation == 512) || (evaluation == -512) ){
					  out = evaluation;
					  i = 6;
					  break;
				  }else out+= evaluation;
			  }

		  }

		  if( !( (out == 512) || (out == -512) ) ){
			if(no0==0)
				out = 0;
			else{
				if(bot == playing)
					out+=16;
				else out-=16;			
			}
		  }

		  return out;
	   }
	   
	   //Prints a Board.
	   public static void mprint(int M[][]){
		   System.out.println();
		   System.out.println("*********");
		   for(int i = 0; i<6; i++){
			System.out.print("*");
			for(int j = 0; j<7; j++)
			  if(M[i][j] == 0)
				System.out.print("-");
			  else if(M[i][j] == 1)
				System.out.print("O");
			  else if(M[i][j] == 2)
				System.out.print("X");
			System.out.println("*");
		   }
	   }
	   
	   //Copy a Matrix into another, auxiliary function.
	   public static void copyM(int orig[][],int dest[][]){

		  for(int i = 0; i<6; i++)
			for(int j = 0; j<7; j++)
				dest[i][j] = orig[i][j];


	   }
	   
	   //Checks if a player can play in a column. Receives a board, which player and the column number. Returns the position of play.
	   public static pos playpos(int board[][],int player,int position){

		   pos out = null;

		   if(board[0][position] == 0){
			   for(int i = 5;i>=0;i--)
				if(board[i][position] == 0){
					out = new pos(i,position,player);
					break;
				}
		   }

		   return out;
	   }
	   
	   //Makes a play in a board. Receives a board and the position of play (who is playing is contained in the data structure pos). Returns Board with play done.
	   public static int[][] play(int board[][],pos p){

		   int out[][] = null;
		   if(p !=null)
			   if(board[p.i][p.j] == 0){
				    out = new int[6][7];
					copyM(board,out);
					out[p.i][p.j] = p.player;
			   }

		   return out;
	   }

	   //Returns an array of possible descendants of a Board. Receives a Board and who is playing.
	   public static int[][][] Descendants(int M[][],int player){

		   int out[][][] = new int[7][6][7];
           for(int j=0;j<7;j++)
              out[j]=play(M,playpos(M,player,j));

		   return out;
	   }
	   
	   //Player Actions. Receives a scanner and who is the player. Returns position played.
	   public static pos playerturn(Scanner sc,int playerno) {

		   pos p = null;

		   System.out.println("Where would you like to play? (1-7)");
		   int x = sc.nextInt();
           x--;

		   if((0<=x) && (x<=6)) {

			   p = playpos(game,playerno,x);
			   int temp[][] = play(game,p);

			   if(temp != null)
				   game = temp;
			   else {
				   System.out.println("Invalid action, choose another column.");
				   return playerturn(sc,playerno);
			   }

		   }
		   else  return playerturn(sc,playerno);

		   return p;
	   }

	   //MiniMax Algorithm. Returns Column chosen.
	   public static int minMax(int Board[][],int PlayerNo){
		   
		   int desc[][][] = Descendants(Board,PlayerNo);
		   int maxValue = -600;
		   int out = 0;
		   
		   
		   int nextplayer;
		   
		   if(PlayerNo==2)
               nextplayer=1;
		   else nextplayer=2;
		   
		   
		   for(int i=0;i<7;i++){
			   
			   int descvalue = 0;
			   
               if(desc[i]!=null){
            	   
            	  int evaluation = evaluate(desc[i],PlayerNo,PlayerNo);
        		   
        		  if((difficulty == 1) || (evaluation == 0)  || (evaluation == 512)  || (evaluation == -512))
            		  descvalue = evaluation;
            	  else
            		  descvalue = minMaxDFS(desc[i],nextplayer,2,PlayerNo);
        		  
        		  //System.out.println("Coluna " + (i+1) + " tem valor " + descvalue);
        		    
        		  if(descvalue >= maxValue) {        			  
        			  maxValue = descvalue;
        			  out = i;  
        		  }
        
               }    
		   }
		   
		   
		   
		   if(desc[out]!=null)
			   return out;
		   else {
			   
			   for(int i=0;i<7;i++)
				   if(desc[i]!=null){
					   out = i;
					   break;
				   }
			   
			   return out;
		   }
                   
	   }
	   
	   //MiniMax Search Function.
       public static int minMaxDFS(int Board[][],int player,int depth,int PlayerNo){
           
    	   if(player>2)
               player=1;
    	   
    	   int desc[][][] = Descendants(Board,player);
    	   int value;
    	   
    	   if(player == PlayerNo) {
    		   value = -600;
    	   }
    	   else value = 600;
    	   
		   for(int i=0;i<7;i++){
			   
			   int descvalue = 0;
			   
               if(desc[i]!=null){
            	   
            	  int evaluation = evaluate(desc[i],PlayerNo,player);

         		  if((difficulty == depth) || (evaluation == 0)  || (evaluation == 512)  || (evaluation == -512))
         			 descvalue = evaluation;
            	  else
            		  descvalue = minMaxDFS(desc[i],(player + 1),(depth + 1),PlayerNo);
         		  
         		 if(player == PlayerNo) {
         			 if(descvalue >= value)
         				 value = descvalue;
         		 }
         		 else {
         			if(descvalue <= value)
        				 value = descvalue;  
         		 }
         		 
               }    
		   }
		   
    	   return value;
		   
       }
  
       //Alpha Beta Pruning Algorithm. Returns Column chosen.
       public static int AB(int Board[][],int PlayerNo){
		   
		   int desc[][][] = Descendants(Board,PlayerNo);
		   int maxValue = -600;
		   int out = 0;
		   int alpha =-600, beta=600;
		   
		   int nextplayer;
		   
		   if(PlayerNo==2)
               nextplayer=1;
		   else nextplayer=2;
		   
		   
		   for(int i=0;i<7;i++){
			   
			   int descvalue = 0;
			   
               if(desc[i]!=null){
            	   
            	  int evaluation = evaluate(desc[i],PlayerNo,PlayerNo);
        		   
        		  if((difficulty == 1) || (evaluation == 0)  || (evaluation == 512)  || (evaluation == -512))
            		  descvalue = evaluation;
            	  else  // if(maxValue < beta )
            		  descvalue = ABDFS(desc[i],nextplayer,2,PlayerNo,alpha,beta);
        		  
        		  //System.out.println("Coluna " + (i+1) + " tem valor " + descvalue);
        		    
        		  if(descvalue >= maxValue) {        			  
        			  maxValue = descvalue;
        			  out = i;  
        		  }
        
               }    
		   }
		   
		   
		   
		   if(desc[out]!=null)
			   return out;
		   else {
			   
			   for(int i=0;i<7;i++)
				   if(desc[i]!=null){
					   out = i;
					   break;
				   }
			   
			   return out;
		   }
                   
	   }
       
       //Alpha Beta Search
       public static int ABDFS(int Board[][],int player,int depth,int PlayerNo,int alpha, int beta){
           
    	   if(player>2)
               player=1;
    	   
    	   int desc[][][] = Descendants(Board,player);
    	   int value;
    	   
    	   if(player == PlayerNo) 
    		   value = -600;    	   
    	   else 
			   value = 600;
    	   
		   for(int i=0;i<7;i++){
			   
			   int descvalue = 0;
			   
               if(desc[i]!=null){
            	   
            	  int evaluation = evaluate(desc[i],PlayerNo,player);
            	  

         		  if((difficulty == depth) || (evaluation == 0)  || (evaluation == 512)  || (evaluation == -512))
         			 descvalue = evaluation;
            	  else if(player == PlayerNo){
					  if(value < beta)
						descvalue = ABDFS(desc[i],(player + 1),(depth + 1),PlayerNo,value,beta);
				  }
				  else if(value > alpha)
					descvalue = ABDFS(desc[i],(player + 1),(depth + 1),PlayerNo,alpha,value);
         		  
         		 if(player == PlayerNo) {
         			 if(descvalue >= value)
         				 value = descvalue;
         		 }
         		 else {
         			if(descvalue <= value)
        				 value = descvalue;  
         		 }
         		 
               }    
		   }
		   
    	   return value;
		   
       }

       //AI actions.
	   public static pos AIturn(int curplayer) {
           pos p = new pos(0,0,0);
		   System.out.print("AI is thinking... ");
           if(ai==2){
                System.out.println("AlphaBeta...");
                p = playpos(game,curplayer,AB(game,curplayer));
           }else{
                System.out.println("MinMax...");
                p = playpos(game,curplayer,minMax(game,curplayer));
           }
		   game = play(game,p);

		   return p;

	   }

	   //Checks if it's a Win based on last position played. Receives a board and last position played. Returns 1 if win, 0 otherwise.
	   public static int winP(int Board[][],pos LaPlace){
		    int sum= 0,win = 0;
		    int i=LaPlace.j-3;
		    if(i<0)
		        i=0;
		    int j=LaPlace.j+3;
		    if(j>6)
		        j=6;
		    for(int wow=i;wow<=j && win!=1;wow++){
		        if(Board[LaPlace.i][wow]==LaPlace.player)
		            sum++;
		        else
		            sum=0;
		        if(sum==4)
		            win=1;
		    }
		    if(win!=1){
		        sum=0;
		        i=LaPlace.i-3;
		        if(i<0)
		            i=0;
		        j=LaPlace.i+3;
		        if(j>5)
		            j=5;
		        for(int wow=i;wow<=j && win!=1;wow++){

		            if(Board[wow][LaPlace.j]==LaPlace.player)
		                sum++;
		            else
		                sum=0;
		            if(sum==4)
		                win=1;
		        }
		    }
		    if(win!=1){
		        int PosLeft=LaPlace.j;
		        int PosVertLeft=LaPlace.i;
		        int a=3;
		        while(PosVertLeft>0 && PosLeft>0 && a>0){
                    PosVertLeft--;
                    PosLeft--;
                    a--;
		        }
		        a+=4;
		        int PosRight=LaPlace.j;
		        int PosVertRight=LaPlace.i;
		        int b=3;
		         while(PosVertRight>0 && PosRight<6 && b>0){
                    PosVertRight--;
                    PosRight++;
                    b--;
		        }
		        b+=4;
		        int Diag1=0;
		        int Diag2=0;
		        while( (PosVertLeft<5 || PosVertRight<5 || PosLeft<6 || PosRight>0) && win!=1 && (a>0 || b>0) ){
		            if(Board[PosVertLeft][PosLeft]==LaPlace.player)
		                Diag1++;
		            else
		                Diag1=0;

		            if(Board[PosVertRight][PosRight]==LaPlace.player)
		                Diag2++;
		            else
		                Diag2=0;

		            if(Diag1==4 || Diag2==4)
		                win=1;



		            if(PosLeft<6 && PosVertLeft<5 && a>0){
		                PosLeft++;
                        PosVertLeft++;
		            }else
		                Diag1=0;
                    a--;

		            if(PosRight>0 && PosVertRight<5 && b>0){
		                PosRight--;
		                PosVertRight++;
		            }else
		                Diag2=0;
                    b--;
		        }
		    }
		    if(win==1)
		        return 1;
		    else
		        return 0;

		}

	   //Checks if it's a draw. Receives a Board. Returns true or false.
	   public static boolean draw(int Board[][]) {

		   boolean out = true;

		   for(int i = 0; i<6; i++)
				for(int j = 0; j<7; j++)
					if(Board[i][j] == 0) {
						out = false;
						i = 7;
						break;
					}

		   return out;
	   }
	   
	   //Checks if the game ended. Receives a Board and last play. Returns 0 if draw, 1 if player 1 won, 2 if player 2 won, -1 otherwise.
	   public static int checkEnd(int Board[][],pos lastplay) {

		   int out = -1;

		   if(winP(Board,lastplay) == 1)
			   out = lastplay.player;
		   else if(draw(Board))
			   out = 0;

		   return out;
	   }

	   public static void main(String[] args){
		   
            int curplayer=1;
		    Scanner sc = new Scanner(System.in);
		    System.out.println("AI using?");
		    System.out.println("MinMax - 1");
		    System.out.println("AlphaBeta - 2");
		    ai= sc.nextInt();
		    while(ai<1 || ai>2){
                System.out.println("Please insert a valid number.");
                ai= sc.nextInt();
		    }
		    System.out.println("Game mode:");
		    System.out.println("Player vs Bot - 1");
		    System.out.println("Bot vs Bot - 2");
		    int mode= sc.nextInt();
		    while(mode<1 || mode>2){
                System.out.println("Please insert a valid number.");
                mode= sc.nextInt();
		    }
		    System.out.println("Choose the difficulty: (1-8)");
			difficulty= sc.nextInt();
		    for(int i = 0; i<6; i++)
				for(int j = 0; j<7; j++)
					game[i][j] = 0;
            if(mode==1){
                System.out.println("Who plays first?");
                System.out.println("Player - 1");
                System.out.println("Bot - 2");
                curplayer = sc.nextInt();
                    while(curplayer<1 || curplayer>2){
                        System.out.println("Please insert a valid number.");
                        curplayer= sc.nextInt();
                    }
            }
            boolean playing = true;

            while(playing){

                pos p = null;

                if(curplayer == 1) {
                    if(mode==1)
                        p = playerturn(sc,curplayer);
                    else
                        p = AIturn(curplayer);
                        curplayer = 2;
                }else {

                    p = AIturn(curplayer);

                    curplayer = 1;
                }

                mprint(game);

                int end = checkEnd(game,p);

                switch(end) {

                    case -1: break;

                    case 0: System.out.println("It's a draw.");
                            playing = false;
                            break;

                    case 1: System.out.println("Player 1 won!");
                            playing = false;
                            break;

                    case 2: System.out.println("Player 2 won!");
                            playing = false;
                            break;

                }
            }

            sc.close();
    }
}


