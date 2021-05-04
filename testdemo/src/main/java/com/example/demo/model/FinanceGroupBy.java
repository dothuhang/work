package com.example.demo.model;

import java.sql.Date;

public interface FinanceGroupBy {
    Date getDate();
    Float getCost();
    Float getAcompte();
    Float getRembourse();
    Float getFacture();
    Float getPenalty();
    Float getState();
}
