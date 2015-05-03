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
def generaGraf(ncliques,N):
    actual = 1
    for i in range(0,ncliques):
        generaNclique(actual,N)
        if(actual>N):afegeixAresta(actual-N,actual,1)
        actual+=N
    afegeixAresta(1,actual-1,1)

generaGraf(30,5)
#genera5clique(1)
