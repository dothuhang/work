import numpy as np



def __kernel(x):
    return 1/(2*np.pi)**0.5*np.exp(-x**2/2)
  
def __kernel_density(X, h, dx):
    f = []
    x = np.arange(min(X),max(X), dx)

    for i in range(len(x)):
        f.append(np.sum(np.array([__kernel((x[i] - r)/h) for r in X])))

    return x, np.array(f)/h/len(X)

def VaR(X, alpha=.95, bandwidth=.01, dx=.05):
    
    x, f = __kernel_density(X, bandwidth, dx)   
    a = 1
    i = len(x) - 1
    while a >= alpha:
        var = x[i]
        a -= f[i]*dx
        i -= 1
    return var

def ES(X, alpha=.95, bandwidth=.01, dx=.05):
    
    x, f = __kernel_density(X, bandwidth, dx)
    
    a = 1
    i = len(x) - 1
    es = 0
    while a >= alpha:
        es += x[i]*f[i]*dx
        a -= f[i]*dx
        i -= 1
    return es/(1 - alpha)