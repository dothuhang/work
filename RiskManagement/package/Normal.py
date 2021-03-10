from scipy.special import erfinv
import numpy as np

def VaR(X, alpha=.95):
    return X.mean() + X.std() * 2**0.5 * erfinv(2*alpha - 1)

def ES(X, alpha=.95):
    return X.mean() + X.std() / (2*np.pi)**0.5 * np.exp(-(2**0.5 * erfinv(2*alpha - 1))**2/2) / (1 - alpha)