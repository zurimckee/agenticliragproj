#include <iostream>

using namespace std;

int findmax(int a[], int n){
    int mx = a[0]; //temp variable, in loop will be the highest number iterated through so far
    int i;
    for (i=1; i<n; i++){ //starts at 1 bc mx is already a[0]
        if(mx < a[i]) mx = a[i]; //if current num is bigger than mx, update mx
    }
    return mx; //return max num

}



int main(){
    int a[] = { 1,3,4,2,10,12,-1,-100}; //declares size of the array, then initalizes values
    int mx = findmax(a, sizeof(a)/sizeof(int)); //sizeof takes the size of the array 
    cout <<"maximum value = " << mx << endl;
    
    return 0;
}
