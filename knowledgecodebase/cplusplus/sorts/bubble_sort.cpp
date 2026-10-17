#include <iostream>
using namespace std;

void print(int a[], int n){
    int i;
    for(i=0;i<n;i++) cout << a[i] << " ";
    cout << endl;
}

//worst case: O(n^2),  best case: O(n), average: O(n^2)
//one of the least efficient sorting options
void bubble_sort (int a[], int n){
    int i, j;
    bool flag = false; //true:swap, false: no swap
    for(i=0;i<n-1;i++){ //outer loop, controls num of passes, after i passes, the last i elements are already sorted
        flag = false;
        for(j=0;j<n-i-1;j++){ //inner loop, goes throuugh the unsorted part of the array, stops earlier aech pass because the last i elements are already in place
            if(a[j] > a[j+1]){ //compares adjacent elements
                swap(a[i],a[i+1]); //swaps if out of order
                flag = true;
            }    
        }
        if (!flag) break; //if no swaps were made, the array is sorted, break the loop
        
    }
    print(a,n);

}

int main(){
    int a[] = {3,10,5,43,1};
    int n = sizeof(a)/sizeof(int); // current number of elements in the array
    print(a, n);
    bubble_sort(a, n);
    

}