import numpy as np
import numpy.random as rd

def simulate(T=1, steps=10, paths=1):
    '''
    Parameters:
    ------------------
    T: float, optional (default=1)
        end point of Brownian motion
    
    steps: int, optional (default=10)
        number of steps to discretize
        
    paths: int, optional (default=1)
        number of paths to simulate
        
    Returns
    ------------------
    2-D array
    '''
    if (paths < 1) or (isinstance(paths, int) == False):
        print("Input Error! Number of paths must be positive and of type int.")
    else:
        z = (T/steps)**.5*rd.randn(paths, steps)
        return np.array([np.cumsum(z[i,:]) for i in range(paths)])
        
