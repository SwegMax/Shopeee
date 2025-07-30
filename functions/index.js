const functions = require('firebase-functions');

const stripe = require('stripe')('SECRET_KEY');

exports.stripePayment = functions.https.onRequest( async (request, response) => {

    const amount = request.query.amount;

    const customer = await stripe.customers.create();
    const ephemeralKey = await stripe.ephemeralKeys.create(
        {customer: customer.id},
        {apiVersion: '2025-05-28.basil'}
    );
    const paymentIntent = await stripe.paymentIntents.create({
        amount: amount,
        currency: 'sgd',
        customer: customer.id,
        automatic_payment_methods: {
        enabled: true,
        },
    });

    response.json({
        paymentIntent: paymentIntent.client_secret,
        ephemeralKey: ephemeralKey.secret,
        customer: customer.id,
        publishableKey: 'pk_test_51Rd5tXRqiw96R7e1gzbcowO1tPJtDh1DCpGGix7xnFdjLAPEyKKGghadYha4EMeF3TSeB0Ct2YdhjKEw15SBAdBh00w2SocSyZ'
    });
});
