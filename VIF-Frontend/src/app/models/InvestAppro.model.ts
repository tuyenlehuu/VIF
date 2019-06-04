import { Customer } from './Customer.model';

export class InvestAppro{
  fullName: string;
  code: string;
  status: number;
  customer: Customer;
  typeOfRequest: number;
  typeOfInvest: number;
  money: number;
  price: number;
  amount: number;
  createDate: Date;

  public constructor(){
    this.typeOfRequest=0;
    this.typeOfInvest=0;
    this.customer = new Customer();
   
  }
}
