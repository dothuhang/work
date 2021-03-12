import numpy as np
import numpy.random as rd
import BrownianMotion
        
class BlackScholes:
    '''
    Attributes
    ------------------
    model: str
        'Black-Scholes'
    '''
    def __init__(self):
        self.model = 'Black-Scholes'
        
      
    def simulate(self,S0, mu, sigma, T=1, steps=1, paths=1):
        '''
        Parameters
        ------------------
        S0: float
            current value of stock price
            
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
        if (paths < 1) or (isinstance(paths, int) == False) or (isinstance(steps, int) == False):
            print("Input Error! Number of steps and paths must be positive and of type int.")
        else:
            t = np.linspace(0,T,steps+1)
            Bt = BrownianMotion.simulate(T, steps, paths)
            St = S0*np.array([np.exp((mu-.5*sigma**2)*t[i] + sigma*Bt[:, i]) for i in range(steps)])
            return np.transpose(St)
        
        
class LocalVolatility:
    def __init__(self):
        self.model = 'LocalVolatility'
        
    def mu(self,t, s):
        pass
    
    def sigma(self, t, s):
        pass
    
    def simulate(self, S0, T=1, steps=1, paths=1):
        '''
        Parameters
        ------------------
        S0: float
            current value of stock price
            
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
        if (paths < 1) or (isinstance(paths, int) == False) or (isinstance(steps, int) == False):
            print("Input Error! Number of steps and paths must be positive and of type int.")
        else:
            t = np.linspace(0,T,steps+1)
            dt = T/steps
            z = (T/steps)**.5*rd.randn(paths, steps)
            St = np.zeros((paths, steps))
            St[:,0] = S0
            
            for i in range(paths):
                for j in range(1, steps):
                    St[i,j] = St[i,j-1] * (1 + self.mu(t[j-1], St[i,j-1])*dt + self.sigma(t[j-1], St[i,j-1])*z[i,j-1])
            return St       