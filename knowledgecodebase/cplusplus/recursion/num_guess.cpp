#include <iostream>
using namespace std;


int g = 4; //integer that is the inputted guess is subtracted from
int n = 1000000000; //high number that represents the bounds of the guess?
int mycount = 0; //guess counter, iterates whenever the guess function is called

int guess(int k){
    cout << mycount << ": " << k << endl; //outputs the current guess, and which num guess it is
    mycount ++; //iterates the count var everytime a guess is outputted
    return g-k; //subtracts guess num from midrange num found in strg func
}

//returns negative if lower, 0 if same, positive if higher
//returns the correct number
int strg(int lo, int hi){ //takes a lo num and a high num as params
    if(lo>hi) return 0; //if low num is higher than high num return 0
    int mid=(lo+hi)/2; //new mid var: adds lo and high then divides by 2 to find the average
    int r = guess(mid); //var r calls guess method with the mid var as a param
    if(r==0) return mid; //if guess-mid is 0 it returns mid because that means that the right num has been guessed
    else if (r<0) return strg(lo,mid-1); //recursive call, if guess-mid < 0 that means that the right num is below the current incorrectly guessed num so it calls the func again,but decreasing the mid because the correct num is less than the previous mid value and also comparing it with lo, because the guess is on the low end
    else return strg(mid+1,hi); //another recursive call, if guess-mid > 0 that means that the right num is above the current incorrectly guessed num so it calls strg again but increasing mid by 1 and comparing it with the high end

}


int main(){
    cout << strg(1,n) << endl; //makes 1 the lower bound, making 100000000 the higher bound
    cout << "Number of guesses="  << mycount << endl;
    return 0;
}