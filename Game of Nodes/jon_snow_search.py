import networkx as nx

class Jon_Snow:
    # constructor
    def __init__(self, Graph, nodelist):
        self.G = Graph
        self.nodes = nodelist

    #search function uses A*
    def search(self):
        jonGraph = nx.Graph()
        list = nx.astar_path(self.G, 'Trader Town', 'The Wall')
        
        # create the graph with the result of the A* list
        for x in list:
            for n in self.nodes:
                if x == n[0]:
                    jonGraph.add_node(n[0], pos=(int(n[1])-2, int(n[2])-2))
        # including the edges
        for x in range(0, len(list) - 1):
            jonGraph.add_edge(list[x], list[x + 1])
        
        return(jonGraph)        

