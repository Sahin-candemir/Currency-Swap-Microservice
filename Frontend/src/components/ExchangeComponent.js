import { useState } from "react";
import { useEffect } from 'react';
import axios from "axios";

function ExchangeComponent() {
    const [soldAccountType, setSoldAccountType] = useState();
    const [receivedAccountType, setReceivedAccountType] = useState();
    const [soldAmount, setSoldAmount] = useState();
    const [exchangeRate, setExchangeRate] = useState();
    const [userId] = useState(localStorage.getItem("currentUser"));
    const [receviedAmountR, setReceivedAmountR] = useState("");
    const [soldAccountTypeR, setSoldAccountTypeR] = useState();
    const [receivedAccountTypeR, setReceivedAccountTypeR] = useState("");
    const [soldAmountR, setSoldAmountR] = useState("");
    const [exchangeRateR, setExchangeRateR] = useState();
    const [exchangeRG, setExchangeRG] = useState("");

    useEffect(() => {
        fetch("/a-service/v1/accounts", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": localStorage.getItem("token"),
            },
        })
            .then(res => res.json())
            .then(
                (result) => {
                    setExchangeRG(result)
                },
                (error) => {
                    console.log(error)
                }
            )
    }, [])

    const handleSoldAccountType = (value) => {
        setSoldAccountType(value);
    }

    const handleEx = () => {

        if ((soldAccountType === 'Dolar' || soldAccountType === 'Euro') && receivedAccountType === 'Tl') {
            setExchangeRate(exchangeRG.rateTlToDolarEuro);
        }                              
        else if (soldAccountType === 'Altin' && receivedAccountType === 'Tl') {
            setExchangeRate(exchangeRG.rateTlToAltın);
        }
        else if (soldAccountType === 'Tl' && (receivedAccountType === 'Dolar' || receivedAccountType === 'Euro')) {
            setExchangeRate(exchangeRG.rateDolarEuroToTl);
        }
        else if ((soldAccountType === 'Dolar' || soldAccountType === 'Euro') && (receivedAccountType === 'Dolar' || receivedAccountType === 'Euro')) {
            setExchangeRate(Math.floor(Math.random() * 1) + 0.97);
        }
        else if (soldAccountType === 'Altin' && (receivedAccountType === 'Dolar' || receivedAccountType === 'Euro')) {
            setExchangeRate(exchangeRG.rateDolarEuroToAltın);
        }
        else if (soldAccountType === 'Tl' && receivedAccountType === 'Altin') {
            setExchangeRate(exchangeRG.rateAltınToTl);
        }
        else if ((soldAccountType === 'Dolar' || soldAccountType === 'Euro') && receivedAccountType === 'Altin') {
            setExchangeRate(exchangeRG.rateAltınToEuroDolar);
        }

        console.log(soldAccountType);
    }

    const onClickExchange = event => {
        event.preventDefault();
        const body = {
            soldAccountType,
            receivedAccountType,
            soldAmount,
            exchangeRate,
            userId
        }
        axios.post("/v1/transactions", body, {

            headers: {
                "Content-Type": "application/json",
                "Authorization": localStorage.getItem("token")
            },
        })

            .then(
                res => {

                    setSoldAccountTypeR(res.data.soldAccountType);
                    setReceivedAccountTypeR(res.data.receivedAccountType);
                    setSoldAmountR(res.data.soldAmount);
                    setReceivedAmountR(res.data.receivedAmount);
                    setExchangeRateR(res.data.exchangeRate);

                    console.log(res.data.soldAmount);
                    console.log(soldAccountTypeR);
                },
                (error) => {
                    console.log(error)
                }
            )
            .catch(err => console.log(err))
    }
    return (
        <div className="container">
            <form>
                <h1 className='text-center'>Exchange</h1>
               
                <div className="form-group">
                    <label for="exampleFormControlSelect1">Sold Account Type</label>
                    <select className="form-control" id="exampleFormControlSelect1" onChange={event => setSoldAccountType(event.target.value)}>
                    
                        <option>Dolar</option>
                        <option>Euro</option>
                        <option>Tl</option>
                        <option>Altin</option>
                    </select>
                </div>
   
                <div class="form-group">
                    <label for="exampleFormControlSelect1">Received Account Type</label>
                    <select className="form-control" id="exampleFormControlSelect1" onChange={event => setReceivedAccountType(event.target.value)}>
                        <option>Dolar</option>
                        <option>Euro</option>
                        <option>Tl</option>
                        <option>Altin</option>
                    </select>
                </div>
                <div className='form-group'>
                    <label>Sold Amount</label>
                    <input onMouseDown={handleEx} className='form-control' name="soldAmount" onChange={event => setSoldAmount(event.target.value)} />
                </div>
                <div>
                    <label>{exchangeRate}</label>
                </div>
                <div>
                    <button className='btn btn-primary' onClick={onClickExchange}>Exchange</button>
                </div>

                <div>
                    <span className="badge badge-success">{receivedAccountTypeR}</span>
                    <label>@{receviedAmountR}</label>
                    <label>@{exchangeRateR}</label>

                </div>
                <div>
                    <span className="badge badge-pill badge-danger">{soldAccountTypeR}</span>
                    <label>@{soldAmountR}</label>
                    <label>@{1 / exchangeRateR}</label>
                </div>
            </form>
        </div>
    );
}
export default ExchangeComponent;