import { Customer } from './Customer.model';


export class BillingInfo {
    id: number;
    activeFlg: number;
    accountName: string;
    bankName: string;
    bankBranch: string;
    bankAccount: string;
    updateDate: Date;
    customer: Customer;

    public constructor() {
        this.activeFlg = 1;
        this.customer = new Customer();
    }
}