#include <iostream>
using namespace std;

int factorial(int k){
    if (k == 0) return 1;
    else return k * factorial(k-1);
}

int main(){
    cout << factorial(5) << endl;
    
}