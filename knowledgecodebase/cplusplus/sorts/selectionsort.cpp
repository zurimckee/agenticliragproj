 //selection sort doesn't just move a chosen element into a different location, it swaps the location of the two elements being compared, making it more efficient
#include <iostream>
using namespace std;

void print(int a[], int n){
    int i; 
    for(i=0;i<n;i++) cout << a[i] << " ";
    cout << endl;
}

//finds smallest position of smallest number in array
//complexity: worst case: O(n), best case: O(n), avg case: O(n)
int find_index_of_smallest(int a[], int start, int n){
    int p = start; //index of smallest number for now
    int i;

    for(i=start+1;i<n;i++){
        if(a[i] < a[p]) p=i; //if current number is smaller than the smallest number so far, update index
    }

    return p;

}

void selection_sort(int a[], int n){
    int i, j;
    int min = 0;
    for(i=0;i<n-1;i++){
        min = i; //assume the smallest is the first element of the array
        for(j=i+1;j<n;j++){ //starts from the next element bc i is already sorted 
            if(a[j]<a[min]) 
            min = j; //update index of smallest num not element
        }
        if (min != i) swap(a[i], a[min]); //swap the smallest number with the current number

        cout << "Post-Pass" << i+1 << ": ";
        print(a,n);
    }
    
}


int main(){
    int a[] = {3,10,5,43,1};
    int n = sizeof(a)/sizeof(int); // current number of elements in the array
    //int i;
    //int p;
    //print(a, n);
    //selection sort
    //complexity: worst case: O(n^2), best case: O(n^2), avg case: O()
    //for(i=0;i<n;i++){
        //p = find_index_of_smallest(a,i,n);
        //swap(a[i], a[p]); //swap the smallest number with the current number
        //print(a, n); //print the array after each swap
    //}

    selection_sort(a,n);
    
    return 0;
    
   

}

