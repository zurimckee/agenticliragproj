#include <iostream>
using namespace std;

void print(int a[], int n){
    int i;
    for(i=0;i<n;i++) cout << a[i] << " ";
    cout << endl;
}

void reverse(int a[], int n){
    int i, j;
    i = 0; //starts at the front
    j = n-1; //starts at the back

    //swaps a[i] and a[j] until they meet in the middle
    //while 
    while (i < j){
        swap(a[i++], a[j--]); //use a[i] and a[j
        //at the same time you increment it
    }

}

int main(){
    int a[] = {1,3,6,10,20};
    int n = sizeof(a)/sizeof(int);
   
    return 0;
}

/*
int main(){
    int a[] = {1,2,3,4,5,6,7};
    int n = sizeof(a)/sizeof(int);
    print(a, n); // before reverse
    reverse(a, n);
    print(a, n); // after reverse
    return 0;
}
*/
