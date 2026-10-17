#include <iostream>
using namespace std;

int find_min(int a[], int n){
    if(n==1) return a[0]; //if only one element, return that element
    return min(find_min(a, n-1), a[n-1]); //recursive call,, compares last element w/ the min of the rest of the array
    //keeps going until base case is reached, then compares each element on the way back up the call stack

}

int sum(int a[], int n){
    if(n==0) return 0; 
    return sum(a, n-1) + a[n-1]; //recursive call
}

bool find(int a[], int n, int t){
    if (n==0) return false;
    return find(a, n-1, t) || a[n-1] ==t;
}

int main(){
    int a[] = {3,10,1,4,-100, 2};
    int i,n = sizeof(a)/sizeof(int);
    cout << find_min(a,n) << endl;
    cout << sum(a, n) << endl;
    cout << find(a, n, 10) << endl;
    return 0;
}