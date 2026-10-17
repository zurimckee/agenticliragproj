#include <iostream>
using namespace std;

//time complexity: best case: O(1), worst case: O(logn), avg case O(logn)
//space complexity: worst case: O(logn)

//target=num that were looking for, lo = lo end comp num, hi = hi end comp num
int binary_search(int a[],int target, int lo, int hi){
    if(lo > hi) return -1; // if loop finishes and target still isn't found (meaning it isn't in the array) returns -1
    int mid = (lo+hi)/2; // calculates median index based on low and hi params
    if (a[mid]==target) return mid; //if element at a[mid] equals the target that means the num matching target has been found so return num at a[mid]
    else if(a[mid] < target) return binary_search(a, target, mid+1, hi); //if target is greater than element at a[mid] look in the higher half, so call method again but with mid+1 as the lo param
    else return binary_search(a, target, lo,mid-1); // if target is less than element @ a[mid] call binary_search method again but looking in the lower half, so with param lo and mid-1 as the high
} //returns the index of the target element

int factorial(int k){
    if (k == 0) return 1;
    else return k * factorial(k-1);
}
int main(){
    int a[] {1,3,5,7,10,12,15,20,23,50}; //array
    int i,n=sizeof(a)/sizeof(int); //size of array
    cout << binary_search(a,50,0,n-1) << endl; //a=array, 50=target, 0=min, n-1=high bc arrays start at zero, so the last element is n-1
    return 0;
}