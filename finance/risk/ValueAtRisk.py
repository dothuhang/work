import numpy as np
from scipy.special import erfinv

__author__ = "Thu-Hang DO"
__version__ = "0.0.1"
__name__ = "ValueAtRisk"
    
class HistoricalQuantile:
    '''
    class HistoricalQuantile to represent Historical Quantile method to estimate VaR , ES
    
    Attributes
    ---------------
    data: numpy.ndarray
        1-D array represnting historical data
        
        
    Methods
    ---------------
    VaR(alpha=.95, interpolation_method='linear')
        return Value-at-Risk of the input data at alpha-th quantile
    
    ES(alpha=.95, interpolation_method='linear')
        return Expected Shortfall (Condition VaR) of the input data at alpha-th quantile
    
    '''
    def __init__(self, loss):
        '''
        Parameters
        ---------------
        loss: numpy.ndarray
            1-D array represnting historical data
        
        '''
        self.data = loss
        
    def VaR(self, alpha=.95, interpolation_method = 'linear'):
        '''
        Parameters
        ---------------
        alpha: float, optional (default = .95)
            Quantile or sequence of quantiles to compute, which must be between 0 and 1 inclusive.
            
        interpolation_method: str, optional (defaul = 'linear')
            Consult numpy.quantile() for further documentation on interpolation method
            
        '''
        return np.quantile(self.data, alpha, interpolation = interpolation_method)

    def ES(self, alpha=.95, interpolation_method = 'linear'):
        '''
        Parameters
        ---------------
        alpha: float, optional (default = .95)
            Quantile or sequence of quantiles to compute, which must be between 0 and 1 inclusive.
            
        interpolation_method: str, optional (defaul = 'linear')
            Consult numpy.quantile() for further documentation on interpolation method
            
        '''
        var_q = self.VaR(alpha, interpolation_method)
        return self.data[self.data >= var_q].mean()

class Normal:
    '''
    class Normal to represent Normal parametric distribution method to estimate VaR , ES
    
    Attributes
    ---------------
    data: numpy.ndarray
        1-D array represnting historical data
        
        
    Methods
    ---------------
    VaR(alpha=.95)
        return Value-at-Risk of the input data at alpha-th quantile
    
    ES(alpha=.95)
        return Expected Shortfall (Condition VaR) of the input data at alpha-th quantile
    
    '''
    def __init__(self, loss):
        '''
        Parameters
        ---------------
        loss: numpy.ndarray
            1-D array representing historical data
        
        '''
        
        self.data = loss
        
    def VaR(self, alpha=.95):
        '''
        Parameters
        ---------------
        alpha: float, optional (default = .95)
            Quantile to compute, which must be between 0 and 1 inclusive.
            
            
        Returns
        ---------------
        float
        '''
        return self.data.mean() + self.data.std() * 2**0.5 * erfinv(2*alpha - 1)

    def ES(self, alpha=.95):
        '''
        Parameters
        ---------------
        alpha: float, optional (default = .95)
            Quantile to compute, which must be between 0 and 1 inclusive.
            
        Returns
        ---------------
        float
            
        '''
        return self.data.mean() + self.data.std() / (2*np.pi)**0.5 * np.exp(-(2**0.5 * erfinv(2*alpha - 1))**2/2) / (1 - alpha)
    
class KernelDensity:
    '''
    class KernelDensity to represent Gaussian kernel density method to estimate VaR , ES. 
    
    Attributes
    ---------------
    data: numpy.ndarray
        1-D array representing historical data
    
    kernel: numpy.ndarray
        2-D array representing kernel density estimate of object data: kernel[0] (x), kernel[1] (f(x))
        
        
    Methods
    ---------------
    fit(bandwidth=.001, dx=.05)
        fit a kernel density to data array of the object
    
    VaR(alpha=.95)
        return Value-at-Risk of the input data at alpha-th quantile
    
    ES(alpha=.95)
        return Expected Shortfall (Condition VaR) of the input data at alpha-th quantile
    
    '''
    def __init__(self, loss):
        '''
        Parameters
        ---------------
        loss: numpy.ndarray
            1-D array represnting historical data
        
        '''
        self.data = loss
        self.kernel = [[], []]
        
    def __kernel(self, x):
        return 1/(2*np.pi)**0.5*np.exp(-x**2/2)

    def __kernel_density(self, X, h, dx):
        f = []
        x = np.arange(min(X),max(X), dx)

        for i in range(len(x)):
            f.append(np.sum(np.array([self.__kernel((x[i] - r)/h) for r in X])))

        return x, np.array(f)/h/len(X)    


    def fit(self, bandwidth=.001, dx=.05):
        '''
        Parameters
        ---------------
        bandwidth: float, optional (default=.001)
            bandwidth parameter to estimate Kernel density
            
        dx: float, optional (default=.05)
            step size of x-axis of outputed kernel
            
        '''
        self.kernel[0], self.kernel[1] = self.__kernel_density(self.data, bandwidth, dx)
        self.kernel = np.array(self.kernel)
        self.bandwidth = bandwidth
        self.dx = dx
    
    def VaR(self, alpha=.95):
        '''
        Parameters
        ---------------
        alpha: float, optional (default = .95)
            Quantile to compute, which must be between 0 and 1 inclusive.
            
        
        Returns
        ---------------
        float
            
        '''
        if len(self.kernel[0]) != 0:          
            a = 1
            i = len(self.kernel[0]) - 1
            while a >= alpha:
                var = self.kernel[0][i]
                a -= self.kernel[1][i]*self.dx
                i -= 1
            return var
        else:
            print("No kernel fitted! Try KernelDensity.fit()")
            return 0

    def ES(self, alpha=.95):
        '''
        Parameters
        ---------------
        alpha: float, optional (default = .95)
            Quantile to compute, which must be between 0 and 1 inclusive.
            
        Returns
        ---------------
        float
            
        '''
        if len(self.kernel[0]) != 0: 
            a = 1
            i = len(self.kernel[0]) - 1
            es = 0
            while a >= alpha:
                es += self.kernel[0][i]*self.kernel[1][i]*self.dx
                a -= self.kernel[1][i]*self.dx
                i -= 1
            return es/(1 - alpha)    
        else:
            print("No kernel fitted! Try KernelDensity.fit()")
            return 0
    
class EVT:
    '''
    class KernelDensity to represent Gaussian kernel density method to estimate VaR , ES. 
    
    Attributes
    ---------------
    loss: numpy.ndarray
        1-D array representing historical data
        
    threshold: float
        data greater than which will be used in calculating GEV parameters
        
    parameters: numpy.ndarray
        1-D array for (shape, scale, location) parameters of GEV distribution by Pickand estimator
        
        
    Methods
    ---------------
    fit(threshold=0, dx=.05)
        fit GEV distribution to object's data
    
    extremal_index(cluster_size=2)
        return Leadbetter's extremal index estimator
    
    VaR(alpha=.95, extremal_index=1)
        return Value-at-Risk of the input data at alpha-th quantile
    
    ES(alpha=.95, extremal_index=1)
        return Expected Shortfall (Condition VaR) of the input data at alpha-th quantile
    
    '''
    def __init__(self, loss):
        '''
        Parameters
        ---------------
        loss: numpy.ndarray
            1-D array represnting historical data
        
        '''
        self.data = loss
        self.parameters = []

    def __estimateParameters(self, threshold=0, dx=.05):
        def F_l(Z, x, l):
            return 1 - np.array(Z[:4*l-1] - Z[4*l-1] >= x).sum()/4/l

        def G_l(x, xi, sigma):
            if xi == 0:
                    G = 1 - np.exp(-x/sigma)
            elif xi > 0:   
                G = 1 - (1 + xi*x/sigma)**(-1/xi)
            else:
                if x <= sigma/-xi:
                    G = 1 - (1 + xi*x/sigma)**(-1/xi)
                else:
                    G = 1

            return G
        Z = -np.sort(-self.data)
        d_l = []
        upper = Z[0] #threshold + dx*size
        for l in range(1, int(len(Z)/4)+1):  
            d = []
            for x in np.arange(threshold, upper,dx):
                F = F_l(Z, x, l)
                xi = np.log(2)**-1*np.log((Z[l-1] - Z[2*l-1])/(Z[2*l-1] - Z[4*l-1]))
                sigma = (Z[2*l-1] - Z[4*l-1])*xi/(2**xi - 1)
                G = G_l(x, xi, sigma)
                d.append(abs(F-G))
            d_l.append(max(d))
            
        M = np.argmin(np.array(d_l)) + 1
        
        xi = np.log(2)**-1*np.log((Z[M-1] - Z[2*M-1])/(Z[2*M-1] - Z[4*M-1]))
        sigma = (Z[2*M-1] - Z[4*M-1])*xi/(2**xi - 1)

        return xi, sigma, Z[4*M-1]
    
    def fit(self, threshold=0, dx=.05):
        '''
        Parameters
        ---------------
        threshold: float, optional (default=0)
            greater than which data is included in GEV calculation
            
        dx: float, optional (default=.05)
            step size of x-axis for GEV distribution calculation
            
        '''
        self.threshold = threshold
        self.parameters = np.zeros((3,))
        self.parameters[0],self.parameters[1],self.parameters[2] = self.__estimateParameters(threshold, dx)
    
    def extremal_index(self, cluster_size=2):
        '''
        Parameters
        ---------------
        cluster_size: int, optional (default=2)
            minimum number of consecutives exceedances over threshold to be considered a cluster
        
        Returns
        ---------------
        float
        '''
        exceed = 0
        n_exceed = (self.data < -self.parameters[2]).sum()
        n_cluster = 0
        for i in range(len(self.data)-cluster_size):
            if np.array(self.data[i:i+cluster_size] < -self.parameters[2]).sum() == cluster_size:
                n_cluster += 1    

        return n_cluster/n_exceed
    
    def VaR(self, alpha=.95, extremal_index=1):
        '''
        Parameters
        ---------------
        alpha: int, optional (default=.95)
            
        extremal_index: float, optional (default=1)
            refer to KernelDensity.extremal_index() for more detail
            
        Returns
        ---------------
        float
        '''
        if len(self.parameters) != 0:          
            return self.parameters[2] + self.parameters[1]/self.parameters[0]*((len(self.data)/(self.data >= self.parameters[2]).sum()*(1 - alpha)*extremal_index)**-self.parameters[0] - 1)
        else:
            print("No parameters specified! Try EVT.fit()")
            return 0
        
    def ES(self, alpha=.95, extremal_index=1):
        '''
        Parameters
        ---------------
        alpha: int, optional (default=.95)
            
        extremal_index: float, optional (default=1)
            refer to KernelDensity.extremal_index() for more detail
       
        Returns
        ---------------
        float
        '''
        if len(self.parameters) != 0:
            v = self.VaR(alpha, extremal_index)
            return (v + self.parameters[1] - self.parameters[0]*self.parameters[2])/(1-self.parameters[0])
        else:
            print("No parameters specified! Try EVT.fit()")
            return 0
