#Chris Snyder - lok139
# game_of_nodes.py

import white_walker_search as ww
import jon_snow_search as js
import csv
import networkx as nx
import matplotlib.pyplot as plt

csvlist = []
nodes = []
edges = []


# Draws the Graph using the networkx functions
def drawGraph(G, jonPath, wwPath):

    plt.figure(figsize=(10, 6.5))
    img = plt.imread("map.jpg")
    
    # determine positions
    pos = nx.get_node_attributes(G, 'pos')
    jonpos = nx.get_node_attributes(jonPath, 'pos')
    wwpos = nx.get_node_attributes(wwPath, 'pos')
    edge_labels = nx.get_edge_attributes(G, 'weight')
    
    # draws the graph this necessary node and edge characteristics
    nx.draw_networkx_nodes(G, pos, with_labels=True, node_size=2, font_size=6)
    nx.draw_networkx_edges(jonPath, jonpos,  width=2, edge_color='blue')
    nx.draw_networkx_edges(wwPath, wwpos, width=2, edge_color='red')
    nx.draw_networkx_edges(G, pos, width=0.5, edge_color='grey') 
    nx.draw_networkx_labels(G, pos, font_size=7)
    nx.draw_networkx_edge_labels(G, pos, edge_labels=edge_labels, font_size=4, bbox=dict(alpha=0))

    #draws out the graph
    plt.xticks([])
    plt.yticks([])
    plt.imshow(img, aspect='auto', extent=[-20, 880, 20, 780])
    plt.show()


# Prints the paths to the console
def printPaths(nodePath, name, startNode):
    prev = ""

    pathList = nx.dfs_edges(nodePath, startNode)

    print(name + ' path:')
    
    #iterates through the path, printing the node name
    for index, j in enumerate(pathList):
        if index == 0:
            print("   " + j[0])
        elif j[0] != prev:
            print("      Go back to " + j[0])
        print("   " + j[1])
        prev = j[1]

    print("")

    



# Main function
def main():
    #open the csv file and populate data
    with open('data.csv', 'r') as csvfile:
        reader = csv.reader(csvfile)
        csvlist = list(reader)
    
    for index, row in enumerate(csvlist):
        if index < 56:
            nodes.append(row)
        else:
            edges.append(row)
    
    G=nx.Graph()
    
    #create nodes and edges
    for node in nodes:
        G.add_node(node[0],pos=(int(node[1]), int(node[2])))

    for edge in edges:
        G.add_edge(edge[0], edge[1], weight=int(edge[2]))
    
    #create jon and walker objects and begin their searches
    jon = js.Jon_Snow(G, nodes) 
    jonPath = jon.search()
    white = ww.White_Walker(G, nodes)
    whitePath = white.search()
    printPaths(jonPath, 'Jon Snow', 'Trader Town')
    printPaths(whitePath, 'White Walkers', 'The Wall')
    drawGraph(G, jonPath, whitePath)

if __name__ == '__main__':
    main()
