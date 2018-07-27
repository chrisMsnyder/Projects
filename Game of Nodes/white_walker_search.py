import networkx as nx

class White_Walker:
    #constructor
    def __init__(self, Graph, nodelist):
        self.G = Graph
        self.nodes = nodelist

    #searh algorithm, uses Kruskals
    def search(self):
        mst = nx.minimum_spanning_tree(self.G)

        return(mst)
            

