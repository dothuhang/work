# RiskManagement package

## Summary
This package provides useful modules for calculating Value-at-Risk (VaR) and Expected Shortfall (ES). The package includes 4 modules: 
- HistoricalQuantile
- Normal
- KernelDensity
- ExtremeValue.

## News

## Methodology
#### _Historical Quantile_
The method is straightforward: VaR is taken as the value at <img src="https://render.githubusercontent.com/render/math?math=\alpha^{th}">-percentile of the loss empirical distribution given the dataset. The drawback of this method is that it equalises weights to each observation and that the distribution is discrete rather than continuous while the latter shoule be more preferable. Moreover, this method requires a large dataset, often >> <img src="https://render.githubusercontent.com/render/math?math=1/(1-\alpha)">. ES is calculated from the formula:

<img src="https://render.githubusercontent.com/render/math?math=ES_\alpha = E\big[loss \big| loss \geq VaR_\alpha\big]">

###### Usage
```py
from RiskManagement import HistoricalQuantile as hq
import numpy as np

x = np.array([1,2,3,4,5,6,7,8,9,10,11,12])
print(hq.VaR(x, alpha=.99))
print(hq.ES(x, alpha=.99))
```
Expected result:
```
>>>11.89
>>>12.0
```
#### _Normal Distribution_

The method relies on a specific assumption that the log-return distribution <img src="https://render.githubusercontent.com/render/math?math=r_t"> follows Gaussian distribution. The mean <img src="https://render.githubusercontent.com/render/math?math=\mu"> and standard deviation <img src="https://render.githubusercontent.com/render/math?math=\sigma"> is estimated from historical data
<img src="https://render.githubusercontent.com/render/math?math=VaR_\alpha = \mu + \sigma\mathcal{N}^{-1}(\alpha)">

<img src="https://render.githubusercontent.com/render/math?math=ES_\alpha = \mu + \dfrac{\sigma}{1 - \alpha} \dfrac{1}{\sqrt{2\pi}} exp\Big(-\big(\mathcal{N}^{-1}(\alpha)\big)^2/2\Big)">

```py
from RiskManagement import Normal as n
import numpy as np

x = np.array([1,2,3,4,5,6,7,8,9,10,11,12])
print(n.VaR(x, alpha=.99))
print(n.ES(x, alpha=.99))
```
Expected Result
```
>>>14.530675063160272
>>>15.70045949109648
```

#### _Kernel Density_
This method is nonparametrical, the distribution is infered from historical data using kernel method, which utilises Gaussian kernel in this case. 
<img src="https://render.githubusercontent.com/render/math?math=\hat{f}(x) = \frac{1}{nh}\sum\limits_{i=1}^n\mathcal{K}\big(\frac{x-x_i}{h}\big)">

The estimated density is smoothed by an arbitrary parameter (bandwidth) <img src="https://render.githubusercontent.com/render/math?math=h">, smaller <img src="https://render.githubusercontent.com/render/math?math=h"> resulted in closer-to-empirical density estimates.

Having the kernel, VaR and ES is calculated by discretising the density estimate.
```py
from RiskManagement import KernelDensity as kd
import numpy as np

x = np.array([1,2,3,4,5,6,7,8,9,10,11,12])
print(kd.VaR(x, alpha=.99, bandwidth=.001, dx=.05))
print(kd.ES(x, alpha=.99, bandwidth=.001, dx=.05))
```
Expected Result
```
>>>11.000000000000009
>>>1828.4854518398995
```

#### _Extreme Value Theory_

Extreme Value Theory is preferred to estimate VaR and ES at extremes (<img src="https://render.githubusercontent.com/render/math?math=\alpha"> >> 99%)
The following section introduces a widely accepted method to estimate the parameters in GEV method which was introduced by Pickands (1975). In short, they are given by:

<img src="https://render.githubusercontent.com/render/math?math=\xi = \dfrac{1}{\log(2)}\log\bigg(\dfrac{Z_M - Z_{2M}}{Z_{2M} - Z_{4M}}\bigg)">

<img src="https://render.githubusercontent.com/render/math?math=\sigma = \dfrac{Z_{2M} - Z_{4M}}{\int_0^{log 2} e^{\xi u}du} = \dfrac{\xi}{(2^\xi - 1)}\big(Z_{2M} - Z_{4M}\big)">

where Z is the descending ordered statistics (the realised losses). M is chosen so as to minimise the maximal "distance" between the empirical upper tail distribution and the extremal distribution.

<img src="https://render.githubusercontent.com/render/math?math=d_M = \min\limits_{1 \leq l \leq int(n/4)} \max\limits_{x} \Big|\hat{F_l}(x) - \hat{G_l}(x)\Big|">

> Pickands, J., Statistical Inference Using Extreme Order Statistics, Institute of Mathematical Statistics (1975)

Once the parameters are derived, VaR can be calculated directly using the following formula (McNeil 1999):

<img src="https://render.githubusercontent.com/render/math?math=VaR_\alpha = Z_{4M} + \frac{\sigma}{\xi}\bigg(\Big(\frac{n}{N_{Z_{4M}}}(1-\alpha)\Big)^{-\xi} - 1\bigg)">

<img src="https://render.githubusercontent.com/render/math?math=ES_\alpha = \dfrac{VaR_\alpha + \sigma - \xi Z_{4M}}{1 - \xi}">

> McNeil, A.J., Extreme Value Theory for Risk Managers (1999)

```py
from RiskManagement import ExtremeValue as ev
import numpy as np

x = [1,2,3,4,5,6,7,8,9,10,11,12]
evt = ev.EVT(np.array(x))
print(evt.VaR(alpha=.99, threshold=2.0))
print(evt.ES(alpha=.99, threshold=2.0))
```
Expected Result: 3 Pickand estimators and VaR (or ES)
```
([-1.0, 12.0, 1], 12.879999999999999)
([-1.0, 12.0, 1], 12.94)
```
