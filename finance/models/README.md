# Structure

models

|----BrowwnianMotion.py

|----InterestRate.py

|----Stock

## BrownianMotion

#### _function simulate(T=1, steps=10, paths=1)_

```py
from models import BrownianMotion as bm
import matplotlib.pyplot as plt
import numpy as np

T=1; steps=100; paths=3

bt = bm.simulate(T, steps, paths)
plt.plot(np.linspace(0,T,steps), np.transpose(bt))
```

## InterestRate

#### _class Vasicek_

###### _function simulate(r0, theta, alpha, sigma, T=1, steps=100, paths=1)_

```py
from models import InterestRate as ir
import matplotlib.pyplot as plt
import numpy as np

T=3; steps=3000; paths=1; r0=.02; theta=.015; alpha=.75; sigma=.15

rate = ir.Vasicek()

rt = rate.simulate(r0, theta, alpha, sigma, T, steps, paths)

plt.plot(np.linspace(0,T,steps), np.transpose(rt))
```

###### _function calibrate()_

```py
rate.calibrate(rt[0], T/steps)

print(rate.parameters)

```

#### _class CIR_

###### _function simulate(self, r0, theta, alpha, sigma, T=1, steps=100, paths=1)_

```py
from models import InterestRate as ir
import matplotlib.pyplot as plt
import numpy as np

T=3; steps=3000; paths=1; r0=.02; theta=.015; alpha=.75; sigma=.15

rate = ir.CIR()

rt = rate.simulate(r0, theta, alpha, sigma, T, steps, paths)

plt.plot(np.linspace(0,T,steps), np.transpose(rt))
```

#### _class NelsonSiegel_

###### _function calibrate(t, rt, error=10**-6)_
```py
from models import InterestRate as ir
import numpy as np

t = np.array([0, 0.25, 0.5, 0.75, 1,2,3,4,5,6,7,8,9,10])
r = np.array([0.0215, 0.0235, 0.026, 0.0282, 0.0305, 0.0344, 0.038, .0425, .045, .0468, .0482, .0494, .0503, .0511])

x = np.linspace(0,t[-1], 1000)[1:]
ns = ir.NelsonSiegel()
beta = ns.calibrate(t[1:], r[1:], 10**-6)

```
###### _function calculate(t, beta)_

```py
plt.plot(x, ns.calculate(x, beta))
plt.plot(t, r)
```

## Stock

#### _class BlackScholes_

#### _class LocalVolatility_
```py
import numpy as np
import numpy.random as rd
import models

class LD(models._LocalVolatility):
    def __init__(self):
        super().__init__()
            
    def mu(self, t, s):
        return .1
    
    def sigma(self, t, s):
        return 1.7/(s**.8)
    
    
st = LV()
x = st.simulate(10, 1, 100, 1)
plt.plot(np.transpose(x))

```
