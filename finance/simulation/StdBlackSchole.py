import numpy as np
import numpy.random as rd

def BrownianMotion(T=1, steps=10, paths=1):
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
        
class Stock:
    '''
    Parameters
    ------------------
    S0: float
        current value of stock price
    '''
    def __init__(self, S0):
        self.S0 = S0
        
    '''
    Parameters
    ------------------
    mu: float
    	drift (for P-measure) or risk-free rate (for risk-neutral measure)

    sigma: float
        volatility of stock
    
    T: float, optional (default=1)
        time span of simulation period
        
    steps: int, optional (default=1)
        number of steps to discretize
    
    paths: int, optional (default=1)
        number of paths to simulate
        
    Returns
    ------------------
    2-D array
        (paths x steps) matrice represent stock price S[i,j] at time t[j] of path i-th
    '''    
    def simulate(self, mu=0, sigma=0, T=1, steps=1, paths=1):
        if (paths < 1) or (isinstance(paths, int) == False) or (isinstance(steps, int) == False):
            print("Input Error! Number of steps and paths must be positive and of type int.")
        else:
            t = np.linspace(0,T,steps+1)
            Bt = BrownianMotion(T, steps, paths)
            St = self.S0*np.array([np.exp((mu-.5*sigma**2)*t[i] + sigma*Bt[:, i]) for i in range(steps)])
            return np.transpose(St)