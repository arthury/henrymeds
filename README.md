# Henry Meds - Reservation

- Overall, 3 of the 4 tasks were implemented in the allotted time.

- Android 13 T API 33 selected for project, but for a production app I would talk to Product and see
  if they would want more device compatibility.  (API 9 P 28 has 90% device distribution, API 10 Q 29
  has 81%.)

- For this exercise, I assumed that the providers and clients are in the same timezone (i.e.,
  LocalDateTime) to make implementation easier.  In production, I would use something like UTC.

- No unit tests or dependency injection were added for this exercise, which production code should have.

- Spent the majority of time getting ProviderAddScheduleScreen polished.

- I implemented the repository pattern with an example of passing ProvidersState from the data
  source to the view model (a common practice), where the UI will handle the state accordingly.
  This was to demonstrate the possible architecture for a production level app.  I felt writing the
  code would explain things better than writing the implementation details that would probably take
  a similar amount of time to do.

- The rest of the view model functions just call MockBackend directly (no repository pattern) due to
  time.

- ClientListAvailabilityScreen was a quick implementation for this assignment.  A better approach
  is to create a filter for providers' schedules and pass it to the date and time dialogs.  That
  would have used up a lot of time, which is why I implemented this way.

- ClientReservationScreen is mostly implemented and heavily based on ProviderAddScheduleScreen.

- MockBackend.addReservation() works by adding the new reservation to the list data structure.
  As the code stands, there is no way to see the reservation list besides using the debugger.  The
  MockBackend does not iterate through the list of providers' schedules to determine if the reservation
  time is valid due to time constraints.

- For ClientReservationScreen, I am assuming that the backend would return an error if the time slot
  is violating the 24 hour minimum advance limit.  In a production scenario, this API behavior would 
  be decided between the client and backend teams.  The app has a minor preventive measure by
  forwarding the start time by 25 hours in the event the app user idles on the screen to avoid starting
  reservation time creep.  In production, the app would need to handle this app user idling case better
  -- the UI might advance the starting time based on the advancing current time.  Also if the filter
  for providers' schedules was implemented for the date and time dialogs, this reservation feature
  could be implemented directly in the previous ClientListAvailabilityScreen and these two screens
  could be merged.

- Backend should handle expired reservation requests.  When the app submits a reservation, the response
  should include an expiring LocalDateTime value such that the app could clean up expired
  reservations as well.

- I did not have time to implement the client reservation confirmation feature with the mocked backend
  behavior.  A quick and dirty approach would to clone the ClientListAvailabilityScreen, then modify
  the backend to pull the list of unconfirmed reservations.  The app would display the unconfirmed
  list and add a click handler for each row.  If the time had expired, then the app would remove the
  expired entries from the locally fetched list.  It would also display an error to the app user when
  they click on an entry if they idled too long and the slot expired during the idling.  One could
  argue that the confirmation feature is more important than implementing the repository pattern in
  the architecture, but in real life technical debt builds up and is harder & longer causes more
  issues and time delays in long run.

- In a production app, I would use a network client library with file caching and 304 HTTP response
  code handling to reduce network traffic.  Also likely the fetched data would be stored locally in
  a database if the list of available times is large.