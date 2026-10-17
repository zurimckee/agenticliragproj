#include <iostream>
using namespace std;

int fact(int n) {
    if (n == 0) return 1;
    return n * fact(n-1);
}

void reversePrint(string s, int n) {
    if (n == 0) return;
    cout << s[n-1];
    reversePrint(s, n-1);
}

int mystery(int n) {
    if (n <= 1) return 1;
    return mystery(n-1) + mystery(n-2);
}

int return2ndLargest(int a[], int n){
    int i;
    int temp = 0;
    for(i=0;i<n;i++){
        if(a[i]>temp) temp=a[i];
    }
    return return2ndLargest(a,n-1);
}

int main() {
    cout << fact(4) << endl;
    reversePrint("cat", 3);
    cout << mystery(5) << endl;
    int a[] = {1,2,3,4,5,6};
    cout << return2ndLargest(a, 6) << endl;
    
    return 0;
}