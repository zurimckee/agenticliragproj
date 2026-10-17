#include <iostream>
using namespace std;

void print(int a[], int n){
    int i; 
    for(i=0;i<n;i++) cout << a[i] << " ";
    cout << endl;
}

//complexity: worst case:O(n), best case: O(1), average case: O(n)
void insert_into_sorted(int a[], int n, int k){ //insertion sort 
    int i;
    for(i=n-1;i>=0;i--){ //goes down the sorted part of the array
        if(k < a[i]) a[i+1] = a[i]; //shift right if k  is smaller
        else break; //found the position
    }
    a[i+1] = k; //insert k at the found position
}

void insertion_sort(int a[], int n){
    int i,j,k; //i=iterates through unsorted part, j=iterates through sorted part, k=element to insert
    for(i=1;i<n;i++){
        k = a[i]; //element to insert
        j = i-1;
        
        //shifts bigger elements to the right
        while (j >= 0 && a[j] > k){ //finds the position to insert k
            a[j+1] = a[j]; //shifts elements to the right
            j--; //moves to the next element on the left
        }
        a[j+1] = k; //insert k at the found position
    }
	
	
}


int main(){
    int a[] = {1,3,5,10,3,43};
    int n = sizeof(a)/sizeof(int); // current number of elements in the array
    print(a, n); // before adding new elements
    //worst case: o(n^2), best case: o(n), average case: 
    //for(int i=1;i<n;i++){
      //  insert_into_sorted(a, i, a[i]);
    //}
    insertion_sort(a, n);
    print(a, n);
    return 0;
    
   

}