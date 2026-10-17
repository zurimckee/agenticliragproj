#include <iostream>
using namespace std;

class node{
public:
    int data;
    node* next;

    node(){ //constructor, called when new node created
        data = 5; 
        next = NULL; //type casting,casts 0 to a pointer type that points to null
        //pointer to void (void *) points to any kind of type
        //NULL = (void(*) 0; 
    }


    //initializes both data and next
    node(int data, node* next=NULL){
        this -> data = data;
        this -> next = next;
    }
};

class LinkedList{
private:
    int n;
    node* head;


public:
    LinkedList(){
        n = 0;
        head = NULL;
    }


    //time: O(n) because of the while loop, goes through the entire list
    //space: O(1) because no additional space is used
    void push_back(int k){
        node* p = head;
        if(head == NULL) push_front(k); //if the list is empty, push to the front
        while (p->next != NULL){ //keeps moving p from head until p reaches the end of the list
            p = p -> next;
        } 
        p->next = new node(k); //creates a new node at the end of the list
        n++; //increases size of linked list


    }
    //inserts k to the beginning of the linked list
    //time complexity: O(1)
    void push_front(int k){
        head = new node(k, head);
        n++;
    }

    //only pop_front when head is greater than zero
    //removes the head from linked list and returns the value of the head
    //time complexity: O(1)
    int pop_front(){
        if (head == NULL) throw "Empty LinkedList";
        int temp_data = head->data;
        n--;
        head = head-> next;
        node* temp_node = head;
        delete temp_node; //removes old head from memory 
        
        return temp_data;


    }

    int size(){
        return n;
    }

    void print(){
        node* p = head;
        while(p != NULL){
            cout << p->data << "->";
            p = p->next; 
        }
        cout << "NULL" << endl;
    }

};

void print(node* p){ //takes a pointer to the head node of an LL as a param
    while(p != NULL){ //loops until head == NULL meaning there are no more nodes in the list
        //each iteration looks at one node
        cout << p->data << "->"; //p->data accesses the data in the current node
        p = p->next; //goes to the next node, updates head to point to the next node in the list
    }
    cout << "NULL" << endl;
}

//inserts k to the beginning of the linked list head and returns the new head of the linked list
//time complexity: O(1)
node* push_front(node* head, int k){
    return new node(k, head);
    

}

//dynamic memory allocation, use new keyword
int main(){
    //node *head =  new node(); created in the heap memory, data = 0, next=NULL | head now points to new node memory address
    //returns a memory location/address, a pointer
    //head->next = (node*) 2000; don't manually set the memory location bc you probably don't have access 
    node *head = new node(19, new node(5, new node(11))); //sets next value of head to temp, head address points to temp
    print(head);
    //output: 19->5->11->NULL
    //cout << head->data << ", " << head->next << endl;
    //memory location prints as hexadecimal
    // -> used instead of dot notation in dynamic memory allocation
    head = push_front(head,-100); //if you don't assign the func call as head, it'll just print out the original head (19) instead 
    //of the new head -100
    print(head);

    LinkedList L;
    L.push_front(5);
    L.push_front(2);
    L.push_front(9);
    while(L.size() > 0){
        cout << L.pop_front() << endl;
    }
    L.print();

    return 0;
}