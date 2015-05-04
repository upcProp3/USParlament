import sys
import random

def afegeixNode(numNode):
    print(1)
    print(numNode)
def afegeixAresta(n1,n2,p):
    print(2)
    print(n1)
    print(n2)
    print(p)
def generaNclique(nInicial,N):
    i = 0
    while(i<N):
        afegeixNode(nInicial+i)
        i+=1
    for k in range(0,N):
        for j in range(k+1,N):
            afegeixAresta(nInicial+j,nInicial+k,1)
def generaCliqueGraf(ncliques,N):
    actual = 1
    for i in range(0,ncliques):
        generaNclique(actual,N)
        if(actual>N):afegeixAresta(actual-N,actual,1)
        actual+=N
    afegeixAresta(1,actual-1,1)
    print(7)
    print(0)
def generaRandGraf(n,epN):
    for i in range(1,n+1):
        afegeixNode(i)
        i+=1
    sys.stderr.write("Finished adding Nodes \n")
    nedges = 0
    for i in range(1,n+1):
        for j in range(i,n+1):
            if(random.randint(0,epN)==0):
                afegeixAresta(i,j,1)
                nedges+=1
        if(nedges%100==0):
            sys.stderr.write("Edge Iteration finished,"+str(n+1-i)+"remaining\nEdge count:"+str(nedges))
    print(7)
    print(0)
    
if(len(sys.argv) == 1):
    print("Run with h as an argument to show help")
elif(sys.argv[1]=="h"):
    print("\n-Enter h to show help")
    print("-Run with \"c m N\" to generate the input for a circle of m N-cliques joined together with one edge (all edges with weight 1)")
    print("-Run with \"r n epN\" to generate a random graph with weights 1 on its edges where there is a 1/N chance of an edge between two given nodes n1 and n2\n")
elif(sys.argv[1]=="c"):
    generaCliqueGraf(int(sys.argv[2]),int(sys.argv[3]))
elif(sys.argv[1]=="r"):
    generaRandGraf(int(sys.argv[2]),int(sys.argv[3]))
else:
    print("Run with h as an argument to show help")
