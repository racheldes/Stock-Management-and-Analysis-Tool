 We implemented new classes for the gui view, and gui controller. Previously our design had one
 controller, one view, and one model but now our gui controller calls on the methods from
 the initial model and the gui class. We chose to do this, so we could use the features design and
 avoid reimplementing methods that we already have. Our gui uses action listeners to wait for button
 clicks and utilizes text boxes in order to take in info from the user. The only changes we made
 to our existing code was fixing any functional problems we had with getting the composition and
 changed our main method to determine which interface it should use.