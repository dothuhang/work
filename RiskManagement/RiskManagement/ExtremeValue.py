# EVT parameters estimated by Pickands estimator (1975)
import numpy as np

class EVT:
    import numpy as np
    
    def __init__(self, loss):
        self.data = loss

    def __estimateParameters(self, threshold=0, dx=.05, size=100):
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
        upper = threshold + dx*size
        for l in range(1, int(len(Z)/4)+1):  
            d = []
            for x in np.linspace(threshold, upper,dx):
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
    
    def VaR(self, alpha=.95, threshold=0, dx=.05):
        xi, sigma, u = self.__estimateParameters(threshold, dx)
        self.parameters = [xi, sigma, u]
        return self.parameters, u + sigma/xi*((len(self.data)/np.array([self.data >= u]).sum()*(1 - alpha))**-xi - 1)
    
    def ES(self, alpha=.95, threshold=0, dx=.05):
        param, v = self.VaR(alpha, threshold, dx)
        return param, (v + param[1] - param[0]*param[2])/(1-param[0])
    
