#include <iostream>
using namespace std;

int multiply(int x, int n){
    if(n<0) return -multiply(x,-n);
    if (n==0) return 0;
    int total = x + multiply(x, n-1); //adds x to the return value of each multiply call, multiply call gets smaller every time until the base case is reached
    return total;
    
}

int main(){
    cout << multiply(5,9) << endl;
    return 0;

}