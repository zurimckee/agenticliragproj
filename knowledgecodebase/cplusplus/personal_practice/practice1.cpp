//count greater elements
#include <iostream>
using namespace std;

//simple print func for int arrays
void print(int a[], int n){
    int i;
    for(i=0;i<n;i++) cout << a[i] << " ";
    cout << endl;
}

//counts all of the elements in a that are less than x
int count_greater(int a[], int n, int x){
    int i;
    int counter = 0;
    for(i=0;i<n;i++){
        if(a[i] < x ) counter++;
    }
    return counter; 

}


//finds and counts all duplicate elements in the array
int find_all_dups(int a[], int n){
    int i, j;
    int dupe_count = 0; 

    for(i=0;i<n-1;i++){
        for(j=i+1;j<n;j++){
            if(a[i] == a[j]) dupe_count ++;
        }
    }
    return dupe_count;
}

//finds first duplicate in the array
int find_first_dup(int a[], int n){
    int i, j; 
    for(i=0;i<n-1;i++){
        for(j=i+1;j<n;j++){
            if(a[i] == a[j]) return a[i];
            
        }
    }
    return -1;
    


}

void reverse_array(int a[], int n){
    int i;
    for(i=0;i<n;i++){
        swap(a[i], a[n-i]);
    }  
}

int main(){
    int a[] = {3,4,10,5,4,3,1};
    int n = sizeof(a)/sizeof(int); // current number of elements in the array
    print(a, n);
    int count = count_greater(a, n, 3);
    cout << count << endl;

    int dupe = find_all_dups(a, n);
    cout << dupe << endl;

    int sing_dupe = find_first_dup(a,n);
    cout << sing_dupe << endl;

    //cout << reverse_array(a, n) <<endl;

    return 0;
}