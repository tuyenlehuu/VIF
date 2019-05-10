import { Customer } from './Customer.model';

export class InvestAppro{
  fullName: string;
  code: string;
  status: number;
  customer: Customer;
  typeOfRequest: number;
  money: number;
  price: number;
  amount: number;
  createDate: Date;
}