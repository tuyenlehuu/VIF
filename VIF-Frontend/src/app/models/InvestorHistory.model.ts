import { Customer } from './Customer.model';

export class InvestorHistory {
    id: number;
    code: string;
    amountCCQ: number;
    priceOfCCQ: number;
    amountCCQBefore: number;
    priceOfCCQBefore: number;
    typeOfTransaction: string;
    createDate: Date;
    lastUpdate: Date;
    customer: Customer

    public constructor() {
        this.customer = new Customer();
    }
}