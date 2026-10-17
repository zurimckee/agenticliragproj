#include <iostream>
using namespace std;

void print(int a[], int n){
    for (int i=00;i<n;i++){
        cout << a[i] << " " << endl;
    }
}

int find_largest_times(int a[], int n){
    int i;
    int temp, counter = 0;

    for(i=0;i<n;i++){
        if(a[i] > temp) temp = a[i];
    }
    for(i=0;i<n;i++){
        if (a[i] == temp) counter++;

    }
    return counter;
}

void reverse_array(int a[], int n){
    int left = 0;
    int right = n-1;
    while(left < right){
        swap(a[left], a[right]);
        left++;
        right++;
    }

} 

int even_odd(int a[], int n){
    int even = 0;
    int odd  = 0;
    for(int i=0;i<n;i++){
        if(a[i] % 2 == 0) even++;
        else odd++;
    }
    cout << "Even: " << even << endl << "Odd: " << odd << endl;
    return 0;
}


int main(){
    int a[] = {1,2,3,4,5,5,5,5};
    int n = sizeof(a)/sizeof(a[0]);
    cout << find_largest_times(a,n) << endl;
    //print(a,n);
    //reverse_array(a, n);
    //print(a, n);
    even_odd(a,n);

    return 0;
}