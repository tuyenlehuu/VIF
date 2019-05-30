import { Customer } from './Customer.model';
import { InvestRequestService } from '../services/invest.request.service';
import { Asset } from './Asset.model';

export class InvestRequest {

  id: number;
  amount: number;
  customer: Customer;
  typeOfRequest: number;
  typeOfInvest: number;
  createDate: Date;
  status: number;
  price: number;
  money: number;


  public constructor(){
    this.status=1;
    this.customer = new Customer();
   
  }
  
}