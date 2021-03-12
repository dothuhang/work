import numpy as np
import numpy.random as rd
from sklearn.linear_model import LinearRegression as linreg

class _Vasicek:
    '''
    Attributes
    ---------------------------
    model: str
        'Vasicek'
        
    parameters: dict
        {'long term mean': float, 'reversion speed': float, 'volatility': float} parameters of model
        
        
    Methods
    ---------------------------
    calibrate(data, dt)
        calibrate then set model parameters
    
    simulate(theta, alpha, sigma, T=1, steps=100, paths=1)
        return 2-D array (paths x steps) matrix rt[i,j] rate at path i and time step t[j]
    '''
    def __init__(self):
        self.model = 'Vasicek'
    
    def calibrate(self, data, dt):
        '''
        Parameters
        ---------------------------
        data: 1-D array
            historical time series equally distanced

        dt: float
            time step increment between 2 consecutive data points
        '''

        reg = linreg()
        reg.fit(data[:-1].reshape(-1,1), data[1:])
        predict = []
        predict.append(data[0])
        for i in range(1,len(data)):
            predict.append(reg.coef_*predict[i-1] + reg.intercept_)
            
        predict = np.array(predict)    
        ssr = ((predict - data)**2).mean()
        self.parameters = {'long term mean': reg.intercept_/dt, 
                           'reversion speed': -np.log(reg.coef_[0])/dt, 
                           'volatility': ssr[0]*((-2*np.log(reg.coef_[0])/dt/(1 - reg.coef_[0]**2))**.5)}
    
    def simulate(self, r0, theta, alpha, sigma, T=1, steps=100, paths=1):
        '''
        Parameters
        ---------------------------
        r0: float
        theta: float
        alpha: float
        sigma: float
        T: float, optional (default=1)
        steps: int, optional (default=100)
        paths: int, optional (default=1)
        

        Returns
        ---------------------------
        2-D array
        '''
        dt = T/steps
        z = sigma*(dt**.5)*rd.randn(paths, steps)
        rt = np.zeros(shape = (paths, steps))
        rt[:,0] = r0
        for i in range(1,steps):
            rt[:, i] = rt[:, i-1]*(1 - alpha*dt) + alpha*theta*dt + z[:, i]        
        return rt

class _CIR:
    '''
    Attributes
    ---------------------------
    model: str
        'Cox-Ingersoll-Ross'
        
    parameters: dict
        {'long term mean': float, 'reversion speed': float, 'volatility': float} parameters of model
        
        
    Methods
    ---------------------------
    simulate(theta, alpha, sigma, T=1, steps=100, paths=1)
    '''
    def __init__(self):
        self.model = 'Cox-Ingersoll-Ross'
          
    def simulate(self, r0, theta, alpha, sigma, T=1, steps=100, paths=1):
        '''
        Parameters
        ---------------------------
        r0: float
        theta: float
        alpha: float
        sigma: float
        T: float, optional (default=1)
        steps: int, optional (default=100)
        paths: int, optional (default=1)
        

        Returns
        ---------------------------
        2-D array
        '''
        if 2*alpha*theta < sigma**2:
            print('Parameters do not meet model requirement: 2*alpha*theta >= sigma**2')
        else:
            dt = T/steps
            z = sigma*(dt**.5)*rd.randn(paths, steps)
            rt = np.zeros(shape = (paths, steps))
            rt[:,0] = r0
            for i in range(1,steps):
                rt[:, i] = abs(rt[:, i-1]*(1 - alpha*dt) + alpha*theta*dt + z[:, i]*(rt[:, i-1]**.5))
            return rt

class _NelsonSiegel:
    '''
    class NelsonSiegel represent Nelson Siegel Svensson model for interest rate
    
    Attributes
    ---------------------------
    model: str
        short description of model
    beta: 1-D array
        parameters array for model:
        beta[0]: long term rate factor
        beta[1]: rotation factor, difference between short and long term rate
        beta[2]: curvature factor of long-term part
        beta[3]: curvature factor of short-term part
        beta[4]: the reciprocal of scale parameter for steepness (1/taux1)
        beta[5]: the reciprocal of scale parameter for flatness (1/taux2)
    
    
    Methods
    ---------------------------
    calculate(self, t, beta)
        return 1-D array of interest rate by NSS model using given parameters
        
    calibrate(self, t, rt, error=10**-4)
        return 1-D array of model parameters
    '''
    def __init__(self):
        self.model = 'Nelson Siegel Svensson'
        
    def _jacobian(self,t,beta):       
        f = np.zeros(6)
        f[0] = 1
        f[1] = (1-np.exp(-t*beta[4]))/(t*beta[4])
        f[2] = (1-np.exp(-t*beta[4]))/(t*beta[4]) - np.exp(-t*beta[4])
        f[3] = (1-np.exp(-t*beta[5]))/(t*beta[5]) - np.exp(-t*beta[5])
        f[4] = -beta[4]**-2*(beta[1]+beta[2])*(1-np.exp(-t*beta[4]))+(beta[1]+beta[2])*np.exp(-t*beta[4])/beta[4]*t + t**2*beta[2]*np.exp(-t*beta[4])
        f[5] = -beta[5]**-2*beta[3]*(1-np.exp(-t*beta[5])) + beta[3]*np.exp(-t*beta[5])/beta[5]*t + beta[3]*np.exp(-t*beta[5])*t**2

        return f

    def calculate(self, t, beta):
        '''
        Parameters
        ---------------------------
        t: 1-D array or float
        beta: 1-D array
            
        
        Returns
        ---------------------------
        1-D array
            interpolated rate at time t from given parameters
        '''
        return beta[0] + beta[1]*(1-np.exp(-t*beta[4]))/(t*beta[4]) + beta[2]*((1-np.exp(-t*beta[4]))/(t*beta[4]) - np.exp(-t*beta[4])) + beta[3]*((1-np.exp(-t*beta[5]))/(t*beta[5]) - np.exp(-t*beta[5]))
    
    def calibrate(self, t, rt, error=10**-6):
        '''
        Parameters
        ---------------------------
        t: 1-D array
        rt: 1-D array
        error: float, optional (default=10**-6)
        
        Returns
        ---------------------------        
        '''
        if len(rt) <2:
            print('Array must contains at least 2 elements for calibration.')
        elif np.isin([0], t).sum() != 0:
            print('t must not contains 0.')
        elif len(rt) != len(t):
            print('t and rt must be of same size.')
        else:
            n = len(rt)    
            beta = np.ones(6)
            err = 1
            l = 0.1
            iteration = 1
            while err > error:
                r_theory = []
                f = []
                for i in range(n):
                    f.append(self._jacobian(t[i], beta))
                    r_theory.append(self.calculate(t[i], beta))

                f = np.array(f)   
                res = np.array(r_theory) - rt
                d = np.dot(np.dot(np.linalg.inv(np.dot(f.transpose(), f) + l*np.identity(6)), f.transpose()), res)
                beta = beta - d
                err = np.sum(d**2)
                iteration += 1

            print('Total number of iteration: ', iteration)
            return beta
                 
def InterestRate():
    '''
    Parameter
    ---------------------------
    model: str
        specify model for interest rate object
    
    Returns
    --------------------------- 
    an object of either Vasicek, CIR or NelsonSiegel class
    '''
    if model=='Vasicek':
        return _Vasicek()
    elif model=='CIR':
        return _CIR()
    elif model=='NelsonSiegel':
        return _NelsonSiegel()

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
        
class _BlackScholes:
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
            Bt = BrownianMotion(T, steps, paths)
            St = S0*np.array([np.exp((mu-.5*sigma**2)*t[i] + sigma*Bt[:, i]) for i in range(steps)])
            return np.transpose(St)
        
        
class _LocalVolatility:
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