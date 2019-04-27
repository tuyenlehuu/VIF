import { Customer } from './Customer.model';

export class InvestRequest {

  id: number;
  amount: number;
  customer: Customer;
  typeOfRequest: number;
  createDate: Date;
  status: number;
  price: number;
  money: number;

  public constructor(){
    this.status=1;
    this.customer = new Customer();
    this.price=2;
  }
  
}