import urllib
from urllib.request import urlopen
import urllib.parse
import json
import threading
import time

link = "http://www.stunduizmainas.lv/xxxxxx/XXXXXXXXX.php"
f = urllib.request.urlopen(link)
my_file = f.read()
f.close()
data = json.loads(my_file)

changesForClasses = []
fullChanges = dict()


class GetPreviousId:
    previous_id = data[0]['id']


def run_check():
    is_added = False
    full_change_text = ""
    threading.Timer(1500, run_check).start()
    link_c = "http://www.stunduizmainas.lv/xxxxxx/XXXXXXXXX.php"
    fc = urllib.request.urlopen(link_c, timeout=180)
    file_c = fc.read()
    fc.close()
    data_c = json.loads(file_c)
    new_id = data_c[0]['id']
    # Time stamp
    print(time.strftime('%a %H:%M:%S'))
    if GetPreviousId.previous_id != new_id:
        new_record_amount = int(new_id) - int(GetPreviousId.previous_id)
        link_new_changes = "http://www.stunduizmainas.lv/xxxxxx/XXXXXXX.php?Lenght=" + str(new_record_amount)
        new_changes_f = urllib.request.urlopen(link_new_changes, timeout=180)
        new_changes_file = new_changes_f.read()
        new_changes_f.close()
        new_changes_data = json.loads(new_changes_file)
        for change in new_changes_data:
            for key, value in change.items():
                if key == "klase":
                    if value in changesForClasses:
                        is_added = False
                        fullChanges[value] = 'Vairākas izmaiņas ' + value + ' klasei!'
                    else:
                        is_added = True
                        changesForClasses.append(value)

            if is_added:
                # Creating full message
                klase = ""
                for key, value in change.items():
                    if key == "stunda":
                        full_change_text += value + " | "
                    if key == "klase":
                        klase = value
                        full_change_text += value + " | "
                    if key == "prieksmets":
                        full_change_text += value + " | "
                    if key == "datums":
                        datums = value
                    is_added = False

                # Getting date format
                link_c = "http://www.stunduizmainas.lv/xxxxxx/XXXXXXX.php"
                fc = urllib.request.urlopen(link_c, timeout=180)
                file_c = fc.read()
                fc.close()
                data_c = json.loads(file_c)

                # Setting proper date format
                if data_c[0]['dateCurrent'] == datums:
                    datums = data_c[0]['dateCurrentFormat']
                elif data_c[0]['dateNext'] == datums:
                    datums = data_c[0]['dateNextFormat']
                full_change_text += "" + datums

                # Adding message to dictionary
                fullChanges[klase] = full_change_text
                # Clearing text for next iteration
                full_change_text = ''

        # Sending notifications
        for key, value in fullChanges.items():
            message_sent = value
            value = urllib.parse.urlsplit(value)
            value = list(value)
            value[2] = urllib.parse.quote(value[2])
            value = urllib.parse.urlunsplit(value)
            link_c = "http://www.stunduizmainas.lv/xxxxxx/XXXXXX.php?Topic=" + key + "&Msg=" + value
            send_request = urllib.request.urlopen(link_c, timeout=180)
            send_request.close()
            print("Notification sent...")
            print("Klase: " + key + "  |  Message: (" + message_sent + ")")

        # List clearing
        changesForClasses.clear()
        del changesForClasses[:]
        fullChanges.clear()

        GetPreviousId.previous_id = new_id


run_check()
