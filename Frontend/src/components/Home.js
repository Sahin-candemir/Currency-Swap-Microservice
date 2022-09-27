import { useState } from "react";
import { useEffect } from 'react';
import axios from "axios";
import Box from '@mui/material/Box';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';

function Home(props) {
    const [accounts, setAccounts] = useState([]);
    const [userId]=useState(localStorage.getItem("currentUser"));
    const [transactions, setTransactions] = useState([]);
    const [accountType, setAccountType] = useState("");
    const [open, setOpen]=useState(false);
    const getTransactionList = () => {
  
        fetch(`/v1/transactions/${accountType}/${userId}`,{
            method : "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization" : localStorage.getItem("token"),
              },
        })
        
            .then(res => res.json())
            .then(
                
                (result) => {
                    setTransactions(result)
              
                },
                (error) => {
                    console.log(error)
                }
            )
    }

    useEffect(() => {
        fetch(`/a-service/v1/accounts/getAllAccount/${userId}`,{
            method : "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization" : localStorage.getItem("token"),
              },
        })
            .then(res => res.json())
            .then(
                (result) => {
                    setAccounts(result)
                },
                (error) => {
                    console.log(error)
                }
            )
    }, [userId])
    return (
        <div className="container">
            <table className="table">
                <thead className="thead-dark">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Account</th>
                        <th scope="col">Balance</th>
                    </tr>
                </thead>
                <tbody>
                    {accounts.map(account =>
                        <tr >
                            <th >{account.id}</th>
                            <td>{account.accountType}</td>
                            <td>{account.balance}</td>
                            <IconButton
                        aria-label="expand row"
                        size="small"
                        onClick={() =>  {setOpen(!open); setAccountType(account.accountType); getTransactionList(account.accountType); }}
                    >
                        
                    T</IconButton>
                        </tr>
                    )
                    }
                </tbody>
            </table>
            <table>
            <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Box sx={{ margin: 1 }}>
                            <Typography variant="h6" gutterBottom component="div">
                                Transactions
                            </Typography>
                            <Table size="small" aria-label="purchases">
                                <TableHead>
                                    <TableRow> 
                                        <TableCell>Transaction Date</TableCell>
                                        <TableCell>Amount</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {transactions.map((transaction) => (
                                        <TableRow >
                                            <TableCell component="th" scope="row">
                                                {transaction.transactionDate}
                                            </TableCell>
                                            <TableCell>{transaction.amount}</TableCell>
                                        </TableRow>
                                       
                                    ))}
                                </TableBody>                       
                            </Table>                            
                        </Box> 
                    </Collapse>
                </TableCell>
            </table>
        </div>
    );
}
export default Home;