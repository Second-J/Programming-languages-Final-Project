import java.util.Arrays;

public abstract class Checkers {
    protected int[][] board = new int[8][8]; //the data of the board is just an array
    protected int[] moveList = new int[100]; //the next possible moves (100 is just because it won't reach that high)
    //note the int inside the move list will be a 4 digit number assigning ijmn which will be used in move method and others


    protected void move(int i, int j, int m, int n){ //i is the vertical position while j is the horizontal position of the original piece, m and n is where to go.
        this.board[m][n] = this.board[i][j];
        this.board[i][j] = 0;
        if(m == 0 && this.board[m][n] == 2){
            this.board[m][n] = 4;
        }else if(m == 7 && this.board[m][n] == 1){
            this.board[m][n] = 3;
        }
    }

    protected void eat(int i, int j, int m, int n){
        this.board[m][n] = this.board[i][j];
        this.board[i][j] = 0;        
        if(m>i){
            if(n>j){
                this.board[i+1][j+1] = 0;
            }else{ //if(j>n)
                this.board[i+1][j-1] = 0;
            }
        }else{ //if(i>m)
            if(n>j){
                this.board[i-1][j+1] = 0;
            }else{ //if(j>n)
                this.board[i-1][j-1] = 0;
            }
        }
        if(m == 0 && this.board[m][n] == 2){
            this.board[m][n] = 4;
        }else if(m == 7 && this.board[m][n] == 1){
            this.board[m][n] = 3;
        }
    }

    protected int checkWin(){
        int a = 0;
        int b = 0;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(this.board[i][j] == 1 || this.board[i][j] == 3){
                    a++;
                }else if(this.board[i][j] == 2 || this.board[i][j] == 4){
                    b++;
                }
            }
        }
        if(a == 0){return 2;}
        if(b == 0){return 1;}
        return 0;
    }

    protected int getOpen(){
        for(int i=0; i < 100; i++){
            if(this.moveList[i] == 0){
                return i;
            }
        }
        return 0;
    }

    protected void resetArr(){
        for(int i=0; i<100; i++){
            this.moveList[i] = 0;
        }
    }

    protected void checkNext(int pl){ //which player's turn is it
        if(pl == 1){
            for(int i=0; i<8; i++){
                for(int j=0; j<8; j++){
                    if(this.board[i][j] == 1 || this.board[i][j] == 3){
                        this.moveList[this.getOpen()] = checkLU(i, j); //if can move, will put the possible move into the first 0 of movelist array
                        this.moveList[this.getOpen()] = checkRU(i, j); //if can't move, will put 0, therefore not affecting the movelist array
                        if(this.board[i][j] == 3){
                            this.moveList[this.getOpen()] = checkLD(i, j); //king pieces can also move backwards.
                            this.moveList[this.getOpen()] = checkRD(i, j);
                        }
                    }
                }
            }
        }else if(pl == 2){
            for(int i=0; i<8; i++){
                for(int j=0; j<8; j++){
                    if(this.board[i][j] == 2 || this.board[i][j] == 4){
                        this.moveList[this.getOpen()] = checkLD(i, j);
                        this.moveList[this.getOpen()] = checkRD(i, j);
                        if(this.board[i][j] == 4){
                            this.moveList[this.getOpen()] = checkLU(i, j);
                            this.moveList[this.getOpen()] = checkRU(i, j);
                        }
                    }
                }
            }
        }
    }





//after a piece eats another, checks if it can eat more, coped from the Checks, same thing 4 times
    protected int checkEatLU(int i, int j){ 
        if(this.board[i][j] == 1 || this.board[i][j] == 3){
            if(j<2 || i >= 6){ //if can't eat cause already at 2nd row from edge, just to not get error cause array scope
                    return 0;
                }else if(this.board[i+1][j-1] == 2 || this.board[i+1][j-1] == 4){ //possibility of eating
                    if(this.board[i+2][j-2] == 2 || this.board[i+2][j-2] == 1 || this.board[i+2][j-2] == 3 || this.board[i+2][j-2] == 4){ //space occupied, can't eat
                        return 0;
                    }else if(this.board[i+2][j-2] == 0){ //space available
                        return (i*1000 + j*100 + (i+2) *10 + (j-2)); //ijmn format again
                    }
                }
        }else if(this.board[i][j] == 4){
            if(j<2 || i >= 6){ //if can't eat cause already at 2nd row from edge, just to not get error cause array scope
                    return 0;
                }else if(this.board[i+1][j-1] == 1 || this.board[i+1][j-1] == 3){ //possibility of eating
                if(this.board[i+2][j-2] == 2 || this.board[i+2][j-2] == 1 || this.board[i+2][j-2] == 3 || this.board[i+2][j-2] == 4){ //space occupied, can't eat
                    return 0;
                }else if(this.board[i+2][j-2] == 0){ //space available
                    return (i*1000 + j*100 + (i+2) *10 + (j-2)); //ijmn format again
                }
            }
        }else{
            return 0;
        }return 0;
    }

    protected int checkEatRU(int i, int j){ 
        if(this.board[i][j] == 1 || this.board[i][j] == 3){
            if(j>5 || i >= 6){ //if can't eat cause already at 2nd row from edge, just to not get error cause array scope
                    return 0;
                }else if(this.board[i+1][j+1] == 2 || this.board[i+1][j+1] == 4){ //possibility of eating
                if(this.board[i+2][j+2] == 2 || this.board[i+2][j+2] == 1 || this.board[i+2][j+2] == 3 || this.board[i+2][j+2] == 4){ //space occupied, can't eat
                    return 0;
                }else if(this.board[i+2][j+2] == 0){ //space available
                    return (i*1000 + j*100 + (i+2) *10 + (j+2)); //ijmn format again
                }
            }
        }else if(this.board[i][j] == 4){
            if(j>5 || i >= 6){ //if can't eat cause already at 2nd row from edge, just to not get error cause array scope
                    return 0;
                }else if(this.board[i+1][j+1] == 1 || this.board[i+1][j+1] == 3){ //possibility of eating
                if(this.board[i+2][j+2] == 2 || this.board[i+2][j+2] == 1 || this.board[i+2][j+2] == 3 || this.board[i+2][j+2] == 4){ //space occupied, can't eat
                    return 0;
                }else if(this.board[i+2][j+2] == 0){ //space available
                    return (i*1000 + j*100 + (i+2) *10 + (j+2)); //ijmn format again
                }
            }
        }else{
            return 0;
        }return 0;
    }

    protected int checkEatLD(int i, int j){ 
        if(this.board[i][j] == 2 || this.board[i][j] == 4){
            if(j < 2 || i <= 1){ //if can't eat cause already at 2nd row from edge, just to not get error cause array scope
                    return 0;
                }else if(this.board[i-1][j-1] == 1 || this.board[i-1][j-1] == 3){ //possibility of eating
                if(this.board[i-2][j-2] == 2 || this.board[i-2][j-2] == 1 || this.board[i-2][j-2] == 3 || this.board[i-2][j-2] == 4){ //space occupied, can't eat
                    return 0;
                }else if(this.board[i-2][j-2] == 0){ //space available
                    return (i*1000 + j*100 + (i-2) *10 + (j-2)); //ijmn format again
                }
            }
        }else if(this.board[i][j] == 3){
            if(j < 2 || i <= 1){ //if can't eat cause already at 2nd row from edge, just to not get error cause array scope
                    return 0;
                }else if(this.board[i-1][j-1] == 2 || this.board[i-1][j-1] == 4){ //possibility of eating
                if(this.board[i-2][j-2] == 2 || this.board[i-2][j-2] == 1 || this.board[i-2][j-2] == 3 || this.board[i-2][j-2] == 4){ //space occupied, can't eat
                    return 0;
                }else if(this.board[i-2][j-2] == 0){ //space available
                    return (i*1000 + j*100 + (i-2) *10 + (j-2)); //ijmn format again
                }
            }
        }else{
            return 0;
        }return 0;
    }

    protected int checkEatRD(int i, int j){ 
        if(this.board[i][j] == 2 || this.board[i][j] == 4){
            if(j > 5 || i <= 1){ //if can't eat cause already at 2nd row from edge, just to not get error cause array scope
                    return 0;
                }else if(this.board[i-1][j+1] == 1 || this.board[i-1][j+1] == 3){ //possibility of eating
                if(this.board[i-2][j+2] == 2 || this.board[i-2][j+2] == 1 || this.board[i-2][j+2] == 3 || this.board[i-2][j+2] == 4){ //space occupied, can't eat
                    return 0;
                }else if(this.board[i-2][j+2] == 0){ //space available
                    return (i*1000 + j*100 + (i-2) *10 + (j+2)); //ijmn format again
                }
            }
        }else if(this.board[i][j] == 3){
            if(j > 5 || i <= 1){ //if can't eat cause already at 2nd row from edge, just to not get error cause array scope
                    return 0;
                }else if(this.board[i-1][j+1] == 2 || this.board[i-1][j+1] == 4){ //possibility of eating
                if(this.board[i-2][j+2] == 2 || this.board[i-2][j+2] == 1 || this.board[i-2][j+2] == 3 || this.board[i-2][j+2] == 4){ //space occupied, can't eat
                    return 0;
                }else if(this.board[i-2][j+2] == 0){ //space available
                    return (i*1000 + j*100 + (i-2) *10 + (j+2)); //ijmn format again
                }
            }
        }else{
            return 0;
        }return 0;
    }

    //under this is just the same thing 4 times and the constructor
    private int checkLU(int i, int j){ // Checking if piece can move to the left-up, like before, horizontal and vertical position of the original piece for i and j
        if(this.board[i][j] == 1 || this.board[i][j] == 3){ // 3 is the king version of 1
            if(j < 1 || i > 6){ //if already on leftmost of the board
                return 0; //0 here means there isn't a path
            }else if(this.board[i+1][j-1] == 0){ //space available
                return (i*1000 + j*100 + (i+1)*10 + (j-1)); //this format is the thing that will be saved in moveList
            }else if(this.board[i+1][j-1] == 1 || this.board[i+1][j-1] == 3){ //space taken by ally
                return 0;
            }else if(this.board[i+1][j-1] == 2 || this.board[i+1][j-1] == 4){ //possibility of eating
                if(j<2 || i == 6){ //if can't eat cause already at 2nd row from edge, just to not get error cause array scope
                    return 0;
                }else if(this.board[i+2][j-2] == 2 || this.board[i+2][j-2] == 1 || this.board[i+2][j-2] == 3 || this.board[i+2][j-2] == 4){ //space occupied, can't eat
                    return 0;
                }else if(this.board[i+2][j-2] == 0){ //space available
                    return (i*1000 + j*100 + (i+2) *10 + (j-2)); //ijmn format again
                }
            }
        }else if(this.board[i][j] == 4){ // 4 is the king version of 2
            if(j < 1 || i > 6){ //if already on leftmost of the board
                return 0; //0 here means there isn't a path
            }else if(this.board[i+1][j-1] == 0){ //space available
                return (i*1000 + j*100 + (i+1)*10 + (j-1)); //this format is the thing that will be saved in moveList
            }else if(this.board[i+1][j-1] == 2 || this.board[i+1][j-1] == 4){ //space taken by ally
                return 0;
            }else if(this.board[i+1][j-1] == 1 || this.board[i+1][j-1] == 3){ //possibility of eating
                if(j<2 || i == 6){ //if can't eat cause already at 2nd row from edge, just to not get error cause array scope
                    return 0;
                }else if(this.board[i+2][j-2] == 2 || this.board[i+2][j-2] == 1 || this.board[i+2][j-2] == 3 || this.board[i+2][j-2] == 4){ //space occupied, can't eat
                    return 0;
                }else if(this.board[i+2][j-2] == 0){ //space available
                    return (i*1000 + j*100 + (i+2) *10 + (j-2)); //ijmn format again
                }
            }
        }else{ //if the piece is 2
            return 0;
        }return 0;
    }

    private int checkRU(int i, int j){ // Checking if piece can move to the right-up, like before, horizontal and vertical position of the original piece for i and j
        if(this.board[i][j] == 1 || this.board[i][j] == 3){ // 3 is the king version of 1
            if(j > 6 || i > 6){ //if already on rightmost of the board
                return 0; //0 here means there isn't a path
            }else if(this.board[i+1][j+1] == 0){ //space available
                return (i*1000 + j*100 + (i+1)*10 + (j+1)); //this format is the thing that will be saved in moveList
            }else if(this.board[i+1][j+1] == 1 || this.board[i+1][j+1] == 3){ //space taken by ally
                return 0;
            }else if(this.board[i+1][j+1] == 2 || this.board[i+1][j+1] == 4){ //possibility of eating
                if(j>5 || i == 6){ //if can't eat cause already at 2nd row from edge, just to not get error cause array scope
                    return 0;
                }else if(this.board[i+2][j+2] == 2 || this.board[i+2][j+2] == 1 || this.board[i+2][j+2] == 3 || this.board[i+2][j+2] == 4){ //space occupied, can't eat
                    return 0;
                }else if(this.board[i+2][j+2] == 0){ //space available
                    return (i*1000 + j*100 + (i+2) *10 + (j+2)); //ijmn format again
                }
            }
        }else if(this.board[i][j] == 4){ // 4 is the king version of 2
            if(j > 6 || i > 6){ //if already on rightmost of the board
                return 0; //0 here means there isn't a path
            }else if(this.board[i+1][j+1] == 0){ //space available
                return (i*1000 + j*100 + (i+1)*10 + (j+1)); //this format is the thing that will be saved in moveList
            }else if(this.board[i+1][j+1] == 2 || this.board[i+1][j+1] == 4){ //space taken by ally
                return 0;
            }else if(this.board[i+1][j+1] == 1 || this.board[i+1][j+1] == 3){ //possibility of eating
                if(j>5 || i == 6){ //if can't eat cause already at 2nd row from edge, just to not get error cause array scope
                    return 0;
                }else if(this.board[i+2][j+2] == 2 || this.board[i+2][j+2] == 1 || this.board[i+2][j+2] == 3 || this.board[i+2][j+2] == 4){ //space occupied, can't eat
                    return 0;
                }else if(this.board[i+2][j+2] == 0){ //space available
                    return (i*1000 + j*100 + (i+2) *10 + (j+2)); //ijmn format again
                }
            }
        }else{ //if the piece is 2
            return 0;
        }return 0;
    }

    private int checkLD(int i, int j){ // Checking if piece can move to the left-down, like before, horizontal and vertical position of the original piece for i and j
        if(this.board[i][j] == 3){ // 3 is the king version of 1
            if(j < 1 || i < 1){ //if already on leftmost of the board
                return 0; //0 here means there isn't a path
            }else if(this.board[i-1][j-1] == 0){ //space available
                return (i*1000 + j*100 + (i-1)*10 + (j-1)); //this format is the thing that will be saved in moveList
            }else if(this.board[i-1][j-1] == 1 || this.board[i-1][j-1] == 3){ //space taken by ally
                return 0;
            }else if(this.board[i-1][j-1] == 2 || this.board[i-1][j-1] == 4){ //possibility of eating
                if(j < 2 || i == 1){ //if can't eat cause already at 2nd row from edge, just to not get error cause array scope
                    return 0;
                }else if(this.board[i-2][j-2] == 2 || this.board[i-2][j-2] == 1 || this.board[i-2][j-2] == 3 || this.board[i-2][j-2] == 4){ //space occupied, can't eat
                    return 0;
                }else if(this.board[i-2][j-2] == 0){ //space available
                    return (i*1000 + j*100 + (i-2) *10 + (j-2)); //ijmn format again
                }
            }
        }else if(this.board[i][j] == 4 || this.board[i][j] == 2){ // 4 is the king version of 2
            if(j < 1 || i < 1){ //if already on leftmost of the board
                return 0; //0 here means there isn't a path
            }else if(this.board[i-1][j-1] == 0){ //space available
                return (i*1000 + j*100 + (i-1)*10 + (j-1)); //this format is the thing that will be saved in moveList
            }else if(this.board[i-1][j-1] == 2 || this.board[i-1][j-1] == 4){ //space taken by ally
                return 0;
            }else if(this.board[i-1][j-1] == 1 || this.board[i-1][j-1] == 3){ //possibility of eating
                if(j < 2 || i == 1){ //if can't eat cause already at 2nd row from edge, just to not get error cause array scope
                    return 0;
                }else if(this.board[i-2][j-2] == 2 || this.board[i-2][j-2] == 1 || this.board[i-2][j-2] == 3 || this.board[i-2][j-2] == 4){ //space occupied, can't eat
                    return 0;
                }else if(this.board[i-2][j-2] == 0){ //space available
                    return (i*1000 + j*100 + (i-2) *10 + (j-2)); //ijmn format again
                }
            }
        }else{ //if piece is 1
            return 0;
        }return 0;
    }

    private int checkRD(int i, int j){ // Checking if piece can move to the right-down, like before, horizontal and vertical position of the original piece for i and j
        if(this.board[i][j] == 3){ // 3 is the king version of 1
            if(j > 6 || i < 1){ //if already on rightmost of the board
                return 0; //0 here means there isn't a path
            }else if(this.board[i-1][j+1] == 0){ //space available
                return (i*1000 + j*100 + (i-1)*10 + (j+1)); //this format is the thing that will be saved in moveList
            }else if(this.board[i-1][j+1] == 1 || this.board[i-1][j+1] == 3){ //space taken by ally
                return 0;
            }else if(this.board[i-1][j+1] == 2 || this.board[i-1][j+1] == 4){ //possibility of eating
                if(j > 5 || i == 1){ //if can't eat cause already at 2nd row from edge, just to not get error cause array scope
                    return 0;
                }else if(this.board[i-2][j+2] == 2 || this.board[i-2][j+2] == 1 || this.board[i-2][j+2] == 3 || this.board[i-2][j+2] == 4){ //space occupied, can't eat
                    return 0;
                }else if(this.board[i-2][j+2] == 0){ //space available
                    return (i*1000 + j*100 + (i-2) *10 + (j+2)); //ijmn format again
                }
            }
        }else if(this.board[i][j] == 4 || this.board[i][j] == 2){ // 4 is the king version of 2
            if(j > 6 || i < 1){ //if already on rightmost of the board
                return 0; //0 here means there isn't a path
            }else if(this.board[i-1][j+1] == 0){ //space available
                return (i*1000 + j*100 + (i-1)*10 + (j+1)); //this format is the thing that will be saved in moveList
            }else if(this.board[i-1][j+1] == 2 || this.board[i-1][j+1] == 4){ //space taken by ally
                return 0;
            }else if(this.board[i-1][j+1] == 1 || this.board[i-1][j+1] == 3){ //possibility of eating
                if(j > 5 || i == 1){ //if can't eat cause already at 2nd row from edge, just to not get error cause array scope
                    return 0;
                }else if(this.board[i-2][j+2] == 2 || this.board[i-2][j+2] == 1 || this.board[i-2][j+2] == 3 || this.board[i-2][j+2] == 4){ //space occupied, can't eat
                    return 0;
                }else if(this.board[i-2][j+2] == 0){ //space available
                    return (i*1000 + j*100 + (i-2) *10 + (j+2)); //ijmn format again
                }
            }
        }else{ //if piece is 1
            return 0;
        }return 0;
    }





    public Checkers(){ //the constructor assigns the pieces to the board 
        for(int i=0; i<3; i++){
            for(int j=0; j<8; j++){
                if( (i+j) % 2 == 0){
                    this.board[i][j] =1; //1 is the first player
                }
            }
        }
        for(int i=5; i<8; i++){
            for(int j=0; j<8; j++){
                if( (i+j) % 2 == 0){
                    this.board[i][j] =2; //2 is the second player
                }
            }
        }
    }
}