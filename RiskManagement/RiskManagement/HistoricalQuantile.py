import numpy as np

def VaR(X, alpha=.95):
    return np.quantile(X, alpha, interpolation = 'linear')

def ES(X, alpha=.95):
    var_q = VaR(X, alpha)
    return X[X >= var_q].mean()