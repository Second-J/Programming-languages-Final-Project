import java.util.*;
import Checkers;


public class Game extends Checkers{
    
    Scanner sc= new Scanner(System.in);
    public void printB(){ //printing the board
        for(int i=8; i>0; i--){
            for(int j=0; j<8; j++){
               System.out.print(this.board[i-1][j]);
            }
            System.out.println("");
        }
    }
        
    public int picker(){ //choosing letting the user pick a choice
        int x;
        while(true){
            System.out.println("Please input which move you want to do");
            try{
                x = sc.nextInt();

                if(this.moveList[x-1] == 0){
                    return 1/0;
                }return this.moveList[x-1];
            }catch(Exception e){
                System.out.println("Please input a valid choice");
                sc.next();
            }
        }
    }

    //under here is printing the possible moves to be taken (using chess notations)
    public String switchS(int x){
        switch(x){
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            case 4:
                return "E";
            case 5:
                return "F";
            case 6:
                return "G";
            case 7:
                return "H";
        }return "0";
    }
    public void printNotation(int x){
        int a,b,c,d;
        a = x/1000;
        b = x%1000; b = b/100;
        c = x%100; c = c/10;
        d = x%10;

        System.out.print(switchS(b));
        System.out.print(a+1);
        System.out.print("->");
        System.out.print(switchS(d));
        System.out.println(c+1);
    }
    public void printNext(){
        for(int i=0; i<100; i++){
            if(this.moveList[i] == 0){
                break;
            }else{
                System.out.print(i+1);
                System.out.print(". ");
                printNotation(this.moveList[i]);
            }
        }
    }

    public Game(){

    }

    public int toggle(int x){ //toggling 1 and 2 for the player's turn
        if(x == 2){
            return 1;
        }return 2;
    }

    private void turn(int x){ //basically a turn... (needed to make sure when you eat something and you can eat again, you do)
        int i,j,m,n;
        i = x/1000;
        j = x%1000; j = j/100;
        m = x%100; m = m/10;
        n = x%10;
        if(i-m == 2 || i-m == -2){
            while(true){
                this.eat(i, j, m, n);
                this.resetArr();
                this.moveList[this.getOpen()] = checkEatLD(m, n);
                this.moveList[this.getOpen()] = checkEatLU(m, n);
                this.moveList[this.getOpen()] = checkEatRD(m, n);
                this.moveList[this.getOpen()] = checkEatRU(m, n);
                if(this.moveList[0] == 0){
                    break;
                }else{ //if you can eat something again
                    this.printB();
                    this.printNext();
                    int z = this.picker();
                    i = z/1000;
                    j = z%1000; j = j/100;
                    m = z%100; m = m/10;
                    n = z%10;
                }
            }
        }else{this.move(i, j, m, n);}
    }

    //multiplayer version, the main function that will run all the other functions.
    public void multi(){
        int  pl = 1;
        int x;
        int win;
        this.printB();
        while(true){
            this.resetArr();
            this.checkNext(pl);
            if(this.moveList[0]==0){ //if there are no moves to play, really rare, but possible
                pl = this.toggle(pl);
            }else{
                System.out.print("Player ");
                System.out.print(pl);
                System.out.println("'s turn");
                this.printNext();
                x = this.picker();
                this.turn(x);
                pl = this.toggle(pl);
                this.printB();
                win = this.checkWin();
                if(win != 0){
                    System.out.print("Player ");
                    System.out.print(win);
                    System.out.println(" victory!");
                    break;
                }
            }
        }
    }    
}