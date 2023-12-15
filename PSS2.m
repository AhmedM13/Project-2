%The first plot, just the sqrt function before any changes
figure(1);
x = 0:1:25;
y = sqrt(x)
plot (x, y);

%Salting the function
figure(2);
salting_amount = 5.0
salted_y = y + (rand(size(y)) * 2 - 1) * salting_amount;
plot(x, salted_y);

%Smoothing the salted graph
figure(3)
window_value = 10;
% Step 1: Create the moving average filter
moving_average_filter = ones(1, window_value) / window_value;
% Step 2: Apply the filter to the salted data
smoothed_y = filter(moving_average_filter, 1, salted_y);

plot(x, smoothed_y);
